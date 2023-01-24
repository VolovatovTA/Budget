package ru.bysoft.budget.create_update_delete_transactions.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.bysoft.budget.common.errors.IErrorLogger
import ru.bysoft.budget.common.me_info.IMeInfo
import ru.bysoft.budget.common.network.entity.CommonErrorBody
import ru.bysoft.budget.common.util.*
import ru.bysoft.budget.create_update_delete_transactions.data.network.ITransactionApi
import ru.bysoft.budget.create_update_delete_transactions.data.network.ITransactionsCategoryApi
import ru.bysoft.budget.create_update_delete_transactions.data.network.ITransactionsWalletApi
import ru.bysoft.budget.create_update_delete_transactions.navigation.ITransactionNavigation
import ru.bysoft.budget.create_update_delete_transactions.presentation.entity.*
import ru.bysoft.budget.create_update_delete_transactions.presentation.mapper.ITransactionPresentationMapper
import ru.bysoft.budget.create_update_delete_transactions.presentation.mapper.ITransactionWalletPresentationMapper
import ru.bysoft.budget.create_update_delete_transactions.presentation.mapper.ITransactionsCategoryPresentationMapper
import ru.bysoft.budget.uikit.components.currecyfield.entity.CurrencyFieldState
import javax.inject.Inject

@HiltViewModel
class TransactionCreateViewModel @Inject constructor(
    private val navigate: ITransactionNavigation,
    private val categoryApi: ITransactionsCategoryApi,
    private val walletApi: ITransactionsWalletApi,
    private val transactionApi: ITransactionApi,
    private val errorLogger: IErrorLogger,
    private val categoryMapperPresentation: ITransactionsCategoryPresentationMapper,
    private val walletMapper: ITransactionWalletPresentationMapper,
    private val transactionMapper: ITransactionPresentationMapper,
    private val meInfo: IMeInfo
) : TransactionsCommonViewModel(navigate), ITransactionCreateViewModel {

    private val handlerCategory = CoroutineExceptionHandler { _, t ->
        errorLogger.logError(t)
        state.update { it.copy(categoryState = CategoryError) }
    }

    private val handlerWallet = CoroutineExceptionHandler { _, t ->
        errorLogger.logError(t)
        state.update { it.copy(walletFieldState = WalletErrorState) }
    }

    private val handlerTransaction = CoroutineExceptionHandler { _, t ->
        errorLogger.logError(t)

    }

    override fun create() {
        viewModelScope.launch(handlerTransaction) {
            try {
                state.update {
                    it.copy(
                        isLoading = true
                    )
                }
                transactionApi.createTransaction(transactionMapper.toRequest(state.value))
                state.update {
                    it.copy(
                        isLoading = false
                    )
                }
                navigate.back()
            } catch (e: HttpException) {
                state.update {
                    it.copy(
                        toastText = e.response()?.errorBody()?.string()
                            ?.restore<CommonErrorBody>()?.slug
                    )
                }
            }
        }
    }

    override fun initNavParams() {
        state.update {
            it.copy(
                currencyFieldState = it.currencyFieldState.copy(
                    selectedCurrency = getCurrency(meInfo.getCurrentMeInfo()?.settingsData?.currency)
                )
            )
        }
        loadCategories()
        loadWallets()
    }

    private fun loadCategories() {
        viewModelScope.launch(handlerCategory) {
            state.value = state.value.copy(
                categoryState = CategoryWaiting,
                isLoading = true
            )
            val path = when (state.value.typeState) {
                TransactionTypeEnum.EXPENSE -> "expenses"
                TransactionTypeEnum.TRANSFER -> "transfer"
                TransactionTypeEnum.INCOME -> "income"
            }

            val response = categoryApi.getCategories(path)
            state.value =
                state.value.copy(
                    categoryState = categoryMapperPresentation.toPresentation(response),
                    isLoading = false
                )
        }
    }

    private fun loadWallets() {
        viewModelScope.launch(handlerWallet) {
            state.update {
                it.copy(
                    walletFieldState = WalletWaitingState,
                    isLoading = true
                )
            }
            val response = walletApi.getWallets()
            state.update {
                it.copy(
                    walletFieldState = walletMapper.toPresentation(response),
                    isLoading = false
                )
            }
        }
    }

    private fun showExchangesIfNeed() {
        val currentState = state.value
        val selectedCategory = (currentState.categoryState as? CategorySuccess)?.listCategory
            ?.filter { it.isChosen }

        val categoryWithCurrencyDiffWithTransactionCurrency =
            selectedCategory
                ?.filter { it.currency != state.value.currencyFieldState.selectedCurrency?.displayName }
                ?.mapNotNull { getCurrencyByDisplayName(it.currency) } ?: emptyList()

        val walletId = (currentState.walletFieldState as? WalletSuccessState)?.selectedWalletId

        val walletCurrency = (currentState.walletFieldState as? WalletSuccessState)?.list
            ?.firstOrNull { it.id == walletId }
            .takeIf { it?.currency != currentState.currencyFieldState.selectedCurrency }
            ?.currency

        val listWalletCurrencyExceptSelectedCurrency = listOfNotNull(walletCurrency)

        val finalListCurrency =
            (categoryWithCurrencyDiffWithTransactionCurrency + listWalletCurrencyExceptSelectedCurrency).filterSameCurrency()

        state.update {
            it.copy(
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

    override fun setWalletId(id: String) {
        super.setWalletId(id)
        showExchangesIfNeed()
    }
}