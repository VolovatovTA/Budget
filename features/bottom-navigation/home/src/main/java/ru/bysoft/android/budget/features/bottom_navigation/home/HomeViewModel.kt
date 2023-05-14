package ru.bysoft.android.budget.features.bottom_navigation.home

import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.budget.android.api.data.source.network.entity.transactions.TransferTypeEnum
import ru.bysoft.android.budget.common.errors.errorLogger
import ru.bysoft.android.budget.common.me_info.IMeInfo
import ru.bysoft.android.budget.features.bottom_navigation.home.data.me.IHomeMeRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions.ITransactionsRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.data.wallets.IHomeWalletsRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.navigation.IHomeNavigation
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.ToastInfo
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.filters.FilterData
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.filters.FilterState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.filters.TypeFilter
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.IMeState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.MeErrorState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.MeLoadingState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.MeSuccessState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.transactions.*
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.*
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.mapper.IHomePresentationMapper
import javax.inject.Inject

interface IHomeViewModel {
    val toastState: StateFlow<ToastInfo?>
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
    fun updateTransaction(id: String)
    fun deleteTransaction(id: String)
    fun onSettingsClick()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val walletsRepo: IHomeWalletsRepo,
    private val meRepo: IHomeMeRepo,
    private val transactionsRepo: ITransactionsRepo,
    private val navigate: IHomeNavigation,
    private val meInfo: IMeInfo,
    private val mapper: IHomePresentationMapper
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
    override val toastState: MutableStateFlow<ToastInfo?> =
        MutableStateFlow(null)

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
        navigate.toEditWallet(walletId)
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

    override fun updateTransaction(id: String) {
        navigate.toUpdateTransaction(id)
    }

    @OptIn(ExperimentalMaterialApi::class)
    override fun deleteTransaction(id: String) {
        viewModelScope.launch(homeTransactionExceptionHandler) {
            transactionsState.update { transactionState ->
                (transactionState as? TransactionSuccess)?.copy(
                    list = transactionState.list.map { if (it.id == id) it.copy(isWaiting = true) else it }
                ) ?: transactionState
            }
            val result = transactionsRepo.deleteTransaction(id)
            if (result.isSuccess) {
                transactionsState.update { transactionState ->
                    (transactionState as? TransactionSuccess)?.copy(
                        list = transactionState.list.filterNot { it.id == id }
                    ) ?: transactionState
                }
                val data = walletsRepo.getWallets()
                walletsState.update { state ->
                    (state as? WalletsSuccessState)?.copy(
                        list = state.list.map { wallet ->
                            (wallet as? WalletCardPresentation)?.copy(
                                balance = data.firstOrNull { it.id == wallet.walletId }?.balance ?: wallet.balance
                            ) ?: wallet
                        }
                    ) ?: state
                }
            } else {
                transactionsState.update { transactionState ->
                    (transactionState as? TransactionSuccess)?.copy(
                        list = transactionState.list.map {
                            if (it.id == id) it.copy(isWaiting = false) else it
                        }
                    ) ?: transactionState
                }
                toastState.update {
//                    if (result.exceptionOrNull() is HttpException) {
//                        ToastInfo(it?.keyLaunchedEffect?.not() ?: true, "Произошла ошибка на бэке")
//                    } else {
//                        ToastInfo(
//                            it?.keyLaunchedEffect?.not() ?: true,
//                            "Произошла ошибка на фронте"
//                        )
//                    }
                    ToastInfo(
                        it?.keyLaunchedEffect?.not() ?: true,
                        "Произошла ошибка"
                    )
                }
                errorLogger.logError(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        }

    }

    override fun onSettingsClick() {
        navigate.toSettings()
    }

    private fun updateBalancesOnWalletsAfterSuccessfullDeleteTransaction(
        walletIdFrom: String?,
        walletIdTo: String?,
        amount: Float
    ) {
        walletsState.update { walletsState ->
            (walletsState as? WalletsSuccessState)?.copy(
                list = walletsState.list.map { wallet ->
                    if (wallet is WalletCardPresentation) {
                        when (wallet.walletId) {
                            walletIdFrom -> wallet.copy(
                                balance = wallet.balance + amount
                            )

                            walletIdTo -> wallet.copy(
                                balance = wallet.balance - amount
                            )

                            else -> wallet
                        }
                    } else {
                        wallet
                    }
                }
            ) ?: walletsState
        }
    }

    private fun getWallets(isRefresh: Boolean) {
        viewModelScope.launch(homeWalletsExceptionHandler) {
            walletsState.value = WalletsLoadingState(isRefresh)
            val loadedData = walletsRepo.getWallets()
            walletsState.value = WalletsSuccessState(mapper.mapToState(loadedData))
            currentWalletId = loadedData.firstOrNull()?.id ?: ""
            if (walletsState.value is WalletsSuccessState) {
                getTransactions(isRefresh)
            } else {
                transactionsState.value = TransactionError
            }
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
            val currentWalletState = walletsState.value
            if (currentWalletState is WalletsSuccessState && currentWalletState.list.isEmpty()) {
                transactionsState.value = TransactionSuccess(emptyList())
                return@launch
            }
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
            transactionsState.value =
                TransactionSuccess(mapper.mapToInfo(transactions, deleteLambda))
        }
    }

    private val deleteLambda: (value: DismissValue, data: String) -> Boolean = { value, data ->
        when (value) {
            DismissValue.DismissedToStart -> deleteTransaction(data)
            DismissValue.DismissedToEnd -> updateTransaction(data)
            else -> {}
        }
        true
    }

}