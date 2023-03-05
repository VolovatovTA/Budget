package ru.bysoft.budget.features.bottom_navigation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.bysoft.budget.common.errors.errorLogger
import ru.bysoft.budget.common.me_info.IMeInfo
import ru.bysoft.budget.features.bottom_navigation.home.data.me.IHomeMeRepo
import ru.bysoft.budget.features.bottom_navigation.home.data.transactions.ITransactionsRepo
import ru.bysoft.budget.features.bottom_navigation.home.data.transactions.TransferTypeEnum
import ru.bysoft.budget.features.bottom_navigation.home.data.wallets.IHomeWalletsRepo
import ru.bysoft.budget.features.bottom_navigation.home.navigation.IHomeNavigation
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.filters.FilterData
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.filters.FilterState
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.filters.TypeFilter
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.title.IMeState
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.title.MeErrorState
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.title.MeLoadingState
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.title.MeSuccessState
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.transactions.*
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.wallets.IWalletsState
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletsErrorState
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletsLoadingState
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.wallets.WalletsSuccessState
import ru.bysoft.budget.features.bottom_navigation.home.presentation.mapper.mapToInfo
import ru.bysoft.budget.features.bottom_navigation.home.presentation.mapper.mapToState
import javax.inject.Inject

interface IHomeViewModel {
    val walletsState: StateFlow<IWalletsState>
    val meState: StateFlow<IMeState>
    val filterState: StateFlow<FilterState>
    val transactionsState: StateFlow<TransactionsState>
    fun loadData(isRefresh: Boolean = false)
    fun loadTransactions(isRefresh: Boolean = false)
    fun onClickSimpleWallet()
    fun onClickCreateWallet()
    fun onClickEditWallet(walletId: String)
    fun onPositionChanged(walletId: String)
    fun onClickFilter(newValue: Boolean, filter: FilterData)
    fun updateTransaction(info: TransactionInfo)
    fun deleteTransaction(info: TransactionInfo)
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val walletsRepo: IHomeWalletsRepo,
    private val meRepo: IHomeMeRepo,
    private val transactionsRepo: ITransactionsRepo,
    private val navigate: IHomeNavigation,
    private val meInfo: IMeInfo
) : ViewModel(), IHomeViewModel {

    override fun loadData(isRefresh: Boolean) {
        getMeInfo()
        getWallets(isRefresh)
    }

    override fun loadTransactions(isRefresh: Boolean) {
        getTransactions(isRefresh)
    }

    private val homeWalletsExceptionHandler = CoroutineExceptionHandler { _, t ->
        errorLogger.logError(t)
        walletsState.value = WalletsErrorState
    }

    private val homeMeExceptionHandler = CoroutineExceptionHandler { _, t ->
        errorLogger.logError(t)
        meState.value = MeErrorState
    }

    private val homeTransactionExceptionHandler = CoroutineExceptionHandler { _, t ->
        errorLogger.logError(t)
        transactionsState.value = TransactionError
    }

    override val walletsState: MutableStateFlow<IWalletsState> =
        MutableStateFlow(WalletsLoadingState(false))

    override val meState: MutableStateFlow<IMeState> =
        MutableStateFlow(MeLoadingState)

    override val filterState: MutableStateFlow<FilterState> =
        MutableStateFlow(getBasicFilterState())

    override val transactionsState: MutableStateFlow<TransactionsState> =
        MutableStateFlow(TransactionLoading(false))

    private var currentWalletId: String = ""

    private fun getBasicFilterState() = FilterState(
        listFilters = listOf(
            FilterData(
                type = TypeFilter.Out,
                isChecked = true
            ),
            FilterData(
                type = TypeFilter.Transfer
            ),
            FilterData(
                type = TypeFilter.In
            )
        )
    )

    override fun onClickSimpleWallet() {

    }

    override fun onClickCreateWallet() {
        val currentWalletState = walletsState.value
        if (currentWalletState is WalletsSuccessState) {
            navigate.toCreateWallet()
        }
    }

    override fun onClickEditWallet(walletId: String) {

    }

    override fun onPositionChanged(walletId: String) {
        currentWalletId = walletId
        getTransactions(false)
    }

    override fun onClickFilter(newValue: Boolean, filter: FilterData) {
        filterState.value = filterState.value.copy(
            listFilters = filterState.value.listFilters.map {
                if (it == filter) FilterData(it.type, isChecked = newValue, it.isEnabled) else it
            }
        )
        getTransactions(false)
    }

    override fun updateTransaction(info: TransactionInfo) {
        navigate.toUpdateTransaction(info.id)
    }

    override fun deleteTransaction(info: TransactionInfo) {
        transactionsState.update { transactionState ->
            (transactionState as? TransactionSuccess)?.copy(
                list = transactionState.list.filter { it.id != info.id }
            ) ?: transactionState
        }
    }

    private fun getWallets(isRefresh: Boolean) {
        viewModelScope.launch(homeWalletsExceptionHandler) {
            walletsState.value = WalletsLoadingState(isRefresh)
            val loadedData = walletsRepo.getWallets()
            walletsState.value = WalletsSuccessState(loadedData.mapToState())
            currentWalletId = loadedData.firstOrNull()?.id ?: ""
            getTransactions(isRefresh)
        }
    }

    private fun getMeInfo() {
        viewModelScope.launch(homeMeExceptionHandler) {
            meState.value = MeLoadingState
            val meInfoData = meRepo.getMeInfo()
            meInfo.setCurrentMeInfo(meInfoData)
            meState.value = MeSuccessState(meInfoData.name)
        }
    }

    private fun getTransactions(isRefresh: Boolean) {
        viewModelScope.launch(homeTransactionExceptionHandler) {
            transactionsState.value = TransactionLoading(isRefresh)
            val filters = filterState.value
            val transactions = transactionsRepo.getTransactions(
                type = filters.listFilters
                    .filterNot { it.type == TypeFilter.Transfer }
                    .filter { it.isChecked }
                    .map { ",${it.type.nameForBack}" }
                    .takeIf { it.isNotEmpty() }
                    ?.reduce { acc, s -> acc + s }
                    ?.drop(1),
                transferType = when {
                    filters.isTransfersChecked() && !filters.isExpensesChecked() && !filters.isIncomeChecked() -> TransferTypeEnum.ONLY_TRANSFER
                    filters.isTransfersChecked() -> TransferTypeEnum.WITH_TRANSFER
                    !filters.isTransfersChecked() -> TransferTypeEnum.WITHOUT_TRANSFER
                    else -> TransferTypeEnum.WITHOUT_TRANSFER
                },
                walletId = listOf(currentWalletId)
            )
            transactionsState.value = TransactionSuccess(transactions.mapToInfo())
        }
    }

}