package ru.bysoft.android.budget.features.bottom_navigation.home

import android.util.Log
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.budget.android.api.data.source.network.entity.transactions.TransferTypeEnum
import ru.bysoft.android.budget.common.errors.errorLogger
import ru.bysoft.android.budget.common.me_info.IMeInfo
import ru.bysoft.android.budget.common.util.TAG
import ru.bysoft.android.budget.common.util.TransactionTypeEnum
import ru.bysoft.android.budget.features.bottom_navigation.home.data.me.IHomeMeRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions.ITransactionsRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.data.wallets.IHomeWalletsRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.navigation.IHomeNavigation
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.IMeState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.MeErrorState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.MeLoadingState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.title.MeSuccessState
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.transactions.*
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets.*
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.mapper.IHomePresentationMapper
import ru.bysoft.android.budget.uikit.components.rowtab.entity.UiKitRowTabState
import ru.bysoft.android.budget.uikit.components.rowtab.entity.UiKitTabInfo
import javax.inject.Inject

interface IHomeViewModel {
    val toastState: SharedFlow<String>
    val walletsState: StateFlow<IWalletsState>
    val meState: StateFlow<IMeState>
    val filterState: StateFlow<UiKitRowTabState>
    val transactionsState: StateFlow<TransactionsState>
    fun loadData(isRefresh: Boolean = false)
    fun loadTransactions(isRefresh: Boolean = false)
    fun onClickSimpleWallet()
    fun onClickCreateWallet()
    fun onClickEditWallet(walletId: String)
    fun onPositionSelected(walletId: String)
    fun onClickFilter(newValue: Boolean, filter: Int)
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
        Log.d(TAG, "loadData: ")
        getMeInfo()
        getWallets(isRefresh)
    }

    override fun loadTransactions(isRefresh: Boolean) {
        Log.d(TAG, "loadTransactions: ")
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
    override val toastState: MutableSharedFlow<String> =
        MutableSharedFlow()

    override val walletsState: MutableStateFlow<IWalletsState> =
        MutableStateFlow(WalletsLoadingState(false))

    override val meState: MutableStateFlow<IMeState> =
        MutableStateFlow(MeLoadingState)

    override val filterState: MutableStateFlow<UiKitRowTabState> =
        MutableStateFlow(getBasicFilterState())

    override val transactionsState: MutableStateFlow<TransactionsState> =
        MutableStateFlow(TransactionLoading(false))

    private var currentWalletId: String = ""

    private fun getBasicFilterState() = UiKitRowTabState(
        listFilters = TransactionTypeEnum.values()
            .mapIndexed { index, it -> UiKitTabInfo(it.text, index == 0) }
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

    override fun onPositionSelected(walletId: String) {
        walletsState.update {
            (it as? WalletsSuccessState)?.copy(
                currentWalletId = walletId
            ) ?: it
        }
        getTransactions(false)
    }

    override fun onClickFilter(newValue: Boolean, filter: Int) {
        filterState.update {
            it.copy(
                listFilters = filterState.value.listFilters.mapIndexed { index, uiKitTabInfo ->
                    if (index == filter) uiKitTabInfo.copy(isChecked = newValue) else uiKitTabInfo
                }
            )
        }

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
                                balance = data.firstOrNull { it.id == wallet.walletId }?.balance
                                    ?: wallet.balance
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
                toastState.emit("Произошла ошибка")
//                {
//                    if (result.exceptionOrNull() is HttpException) {
//                        ToastInfo(it?.keyLaunchedEffect?.not() ?: true, "Произошла ошибка на бэке")
//                    } else {
//                        ToastInfo(
//                            it?.keyLaunchedEffect?.not() ?: true,
//                            "Произошла ошибка на фронте"
//                        )
//                    }
//                    ToastInfo(
//                        it?.keyLaunchedEffect?.not() ?: true,
//                        "Произошла ошибка"
//                    )
//                }
                errorLogger.logError(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        }

    }

    override fun onSettingsClick() {
        navigate.toSettings()
    }

    private fun getWallets(isRefresh: Boolean) {
        viewModelScope.launch(homeWalletsExceptionHandler) {
            walletsState.value = WalletsLoadingState(isRefresh)
            val loadedData = walletsRepo.getWallets()
            walletsState.value = WalletsSuccessState(
                mapper.mapToState(loadedData),
                loadedData.firstOrNull()?.id ?: ""
            )
            currentWalletId = loadedData.firstOrNull()?.id ?: ""
            if (walletsState.value is WalletsSuccessState) {
                getTransactions(isRefresh)
            } else {
                transactionsState.value = TransactionError
            }
        }
    }

    private fun getMeInfo() {
        Log.d(TAG, "getMeInfo: ")
        viewModelScope.launch(homeMeExceptionHandler) {
            meState.value = MeLoadingState
            val meInfoData = meRepo.getMeInfo()
            meInfo.setCurrentMeInfo(meInfoData)
            meState.value = MeSuccessState(meInfoData)
        }
    }

    private fun getTransactions(isRefresh: Boolean) {
        Log.d(TAG, "getTransactions: ")
        viewModelScope.launch(homeTransactionExceptionHandler) {
            transactionsState.value = TransactionLoading(isRefresh)
            val filters = filterState.value
            val currentWalletState = walletsState.value
            if (currentWalletState is WalletsSuccessState && currentWalletState.list.isEmpty()) {
                transactionsState.value = TransactionSuccess(emptyList())
                return@launch
            }
            val filtersToBack = filters.listFilters
                .filterNot { it.text == TransactionTypeEnum.TRANSFER.text }
                .filter { it.isChecked }
                .map {
                    ",${
                        TransactionTypeEnum.values()
                            .first { categoryTypeEnum -> categoryTypeEnum.text == it.text }.nameForBack
                    }"
                }
                .takeIf { it.isNotEmpty() }
                ?.reduce { acc, s -> acc + s }
                ?.drop(1)

            val transactions = transactionsRepo.getTransactions(
                type = filtersToBack,
                transferType = transferTypeEnum(filters),
                walletId = listOf(
                    (walletsState.value as? WalletsSuccessState)?.currentWalletId ?: ""
                )
            )
            transactionsState.value =
                TransactionSuccess(mapper.mapToInfo(transactions, deleteLambda))
        }
    }

    private fun transferTypeEnum(filters: UiKitRowTabState) =
        when {
            isTransfersChecked(filters) && !isExpenseChecked(filters) && !isIncomeChecked(filters) -> TransferTypeEnum.ONLY_TRANSFER
            isTransfersChecked(filters) -> TransferTypeEnum.WITH_TRANSFER
            !isTransfersChecked(filters) -> TransferTypeEnum.WITHOUT_TRANSFER
            else -> TransferTypeEnum.WITHOUT_TRANSFER
        }

    private fun isTransfersChecked(filters: UiKitRowTabState) =
        filters.listFilters.any { it.text == TransactionTypeEnum.TRANSFER.text }

    private fun isExpenseChecked(filters: UiKitRowTabState) =
        filters.listFilters.any { it.text == TransactionTypeEnum.EXPENSE.text }

    private fun isIncomeChecked(filters: UiKitRowTabState) =
        filters.listFilters.any { it.text == TransactionTypeEnum.INCOME.text }

    private val deleteLambda: (value: DismissValue, data: String) -> Boolean = { value, data ->
        when (value) {
            DismissValue.DismissedToStart -> deleteTransaction(data)
            DismissValue.DismissedToEnd -> updateTransaction(data)
            else -> {}
        }
        true
    }

}