package ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.common.me_info.IMeInfo
import ru.bysoft.android.budget.common.util.*
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.ITransactionApi
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.ITransactionsCategoryApi
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.ITransactionsWalletApi
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.*
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.entity.requests.TransactionExpenseCreateRequest
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.entity.requests.TransactionIncomeCreateRequest
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.entity.requests.TransactionTransferCreateRequest
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.ITransactionNavigation
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.TransactionsCreateNavParams
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionPresentationMapper
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionWalletPresentationMapper
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionsCategoryPresentationMapper
import ru.bysoft.android.budget.uikit.components.currecyfield.entity.CurrencyFieldState
import javax.inject.Inject

@HiltViewModel
class TransactionCreateViewModel @Inject constructor(
    private val navigate: ITransactionNavigation,
    private val transactionApi: ITransactionApi,
    private val transactionMapper: ITransactionPresentationMapper,
    private val errorLogger: IErrorLogger,
    private val meInfo: IMeInfo,
    categoryApi: ITransactionsCategoryApi,
    walletApi: ITransactionsWalletApi,
    categoryMapperPresentation: ITransactionsCategoryPresentationMapper,
    walletMapper: ITransactionWalletPresentationMapper,
    savedStateHandle: SavedStateHandle
) : TransactionsCommonViewModel(
    navigate = navigate,
    errorLogger = errorLogger,
    categoryApi = categoryApi,
    walletApi = walletApi,
    categoryMapperPresentation = categoryMapperPresentation,
    walletMapper = walletMapper
), ITransactionCreateViewModel {

    init {
        val argument = savedStateHandle.get<String>("argument")?.restore<TransactionsCreateNavParams>()!!
        Log.d(TAG, "init: $argument")
        setTypeTransactions(argument.type)
        state.update {
            it.copyWithCurrency(
                currencyFieldState = it.currencyFieldState.copy(
                    selectedCurrency = getCurrency(meInfo.getCurrentMeInfo()?.settingsData?.currency)
                )
            )
        }
    }

    private val handlerTransaction = CoroutineExceptionHandler { _, t ->
        errorLogger.logError(t)
        state.update {
            it.copyWithToast("Произошла ошибка запроса")
        }
    }

    override fun create() {
        viewModelScope.launch(handlerTransaction) {
            try {
                state.update {
                    it.copyWithLoading(
                        isLoading = true
                    )
                }
                when (val request = transactionMapper.toRequest(state.value)) {
                    is TransactionExpenseCreateRequest ->
                        transactionApi.createTransactionExpense(request)
                    is TransactionTransferCreateRequest ->
                        transactionApi.createTransactionTransfer(request)
                    is TransactionIncomeCreateRequest ->
                        transactionApi.createTransactionIncome(request)
                }

                state.update {
                    it.copyWithLoading(
                        isLoading = false
                    )
                }
                navigate.back()
            } catch (e: Throwable) {
                if (e is HttpException) {
                    state.update {
                        it.copyWithToast(
                            toastText = e.response()?.errorBody()?.string()
                        )
                    }
                } else {
                    Log.d(TAG, e.toString())
                }
                state.update {
                    it.copyWithLoading(
                        isLoading = false
                    )
                }
            }
        }
    }

    override fun initNavParams(argument: TransactionsCreateNavParams) {

    }

    private fun showExchangesIfNeed() {
        val currentState = state.value
        val selectedCategory = (currentState.categoryState as? CategorySuccess)?.listCategory
            ?.filter { it.isChosen }

        val categoryWithCurrencyDiffWithTransactionCurrency =
            selectedCategory
                ?.filter { it.currency != state.value.currencyFieldState.selectedCurrency?.displayName }
                ?.mapNotNull { getCurrencyByDisplayName(it.currency) } ?: emptyList()

        // List of selected walletIdes
        val walletIdList = listOfNotNull(
            (currentState.walletToFieldState as? WalletSuccessState)?.selectedWalletId,
            (currentState.walletFromFieldState as? WalletSuccessState)?.selectedWalletId
        )

        val listWalletsInfo = (currentState.walletToFieldState as? WalletSuccessState)?.list.orEmpty()
            .plus((currentState.walletFromFieldState as? WalletSuccessState)?.list.orEmpty())

        // List of currency in selected wallets except selected currency in currency field
        val listWalletCurrencyExceptSelectedCurrency = listWalletsInfo
            .filter { walletIdList.contains(it.id) }
            .filter { it.currency != currentState.currencyFieldState.selectedCurrency }
            .map { it.currency }


        val finalListCurrency =
            (categoryWithCurrencyDiffWithTransactionCurrency + listWalletCurrencyExceptSelectedCurrency)
                .filterSameCurrency()
                .takeIf { currentState.currencyFieldState.selectedCurrency != null }
                .orEmpty()

        state.update {
            it.copyWithExchanges(
                exchangeFieldState = finalListCurrency.map { currency ->
                    ExchangeFieldState(
                        currencyFieldState = CurrencyFieldState(
                            selectedCurrency = currency,
                            list = finalListCurrency
                        )
                    )
                }
            )
        }
    }

    private fun List<BudgetCurrency>.filterSameCurrency(): List<BudgetCurrency> =
        this
            .groupBy { it }
            .map { it.key }

    override fun setCategoriesIds(categoryPresentation: CategoryPresentation) {
        super.setCategoriesIds(categoryPresentation)
        showExchangesIfNeed()
    }

    override fun setCurrency(currency: BudgetCurrency) {
        super.setCurrency(currency)
        showExchangesIfNeed()
    }

    override fun setWalletId(fromId: String?, toId: String?) {
        super.setWalletId(fromId, toId)
        showExchangesIfNeed()
    }
}