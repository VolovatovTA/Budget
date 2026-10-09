package ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels

import ru.bysoft.android.budget.common.errors.IErrorLogger
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.common.data_entity.CurrencyRateData
import ru.bysoft.android.budget.common.errors.handler
import ru.bysoft.android.budget.common.me_info.IMeInfo
import ru.bysoft.android.budget.common.util.restore
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.currency.getCurrency
import ru.bysoft.android.budget.currency.getCurrencyByDisplayName
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.ICategoriesRepo
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.ITransactionsRepo
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.IWalletsRepo
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.entity.TransactionCreateException
import ru.bysoft.android.budget.features.create_update_delete_transactions.R
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.ITransactionNavigation
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.TransactionsCreateNavParams
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.CategoryPresentation
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.CategorySuccess
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.ExchangeFieldState
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.WalletSuccessState
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionPresentationMapper
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionWalletPresentationMapper
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionsCategoryPresentationMapper
import ru.bysoft.android.budget.features.currency_rates.data.ICurrencyRatesRepo
import ru.bysoft.android.budget.uikit.components.textfield.TextFieldState
import kotlin.math.pow

class TransactionCreateViewModel(
    errorLogger: IErrorLogger,
    private val navigate: ITransactionNavigation,
    private val transactionsRepo: ITransactionsRepo,
    categoriesRepo: ICategoriesRepo,
    walletsRepo: IWalletsRepo,
    private val transactionMapper: ITransactionPresentationMapper,
    private val meInfo: IMeInfo,
    private val currencyRatesRepo: ICurrencyRatesRepo,
    categoryMapperPresentation: ITransactionsCategoryPresentationMapper,
    walletMapper: ITransactionWalletPresentationMapper,
    savedStateHandle: SavedStateHandle
) : TransactionsCommonViewModel(
    navigate = navigate,
    errorLogger = errorLogger,
    categoriesRepo = categoriesRepo,
    walletsRepo = walletsRepo,
    categoryMapperPresentation = categoryMapperPresentation,
    walletMapper = walletMapper
), ITransactionCreateViewModel {

    private var currencyRates: CurrencyRateData? = null

    private val transactionCEH = errorLogger.handler {
        toastState.tryEmit(R.string.error_answer)
    }

    init {
        viewModelScope.launch(transactionCEH) {
            currencyRates = currencyRatesRepo.getCurrencyRates()
        }
        val argument =
            savedStateHandle.get<String>("argument")?.restore<TransactionsCreateNavParams>()!!
        setTypeTransactions(argument.type)
        state.update {
            it.copyWithCurrency(
                currencyFieldState = it.currencyFieldState.copy(
                    selectedCurrency = getCurrency(meInfo.getCurrentMeInfo()?.settingsData?.currency)
                )
            )
        }
    }


    override fun create() {
        viewModelScope.launch(transactionCEH) {
            try {
                state.update {
                    it.copyWithLoading(
                        isLoading = true
                    )
                }
                transactionsRepo.create(transactionMapper.toNewTransaction(state.value))
                state.update { it.copyWithLoading(isLoading = false) }
                navigate.back()
            } catch (e: TransactionCreateException) {
                // TODO: show e.fieldErrors next to the fields instead of a generic toast
                errorLogger.logError(e)
                toastState.tryEmit(R.string.error_answer)
                state.update { it.copyWithLoading(isLoading = false) }
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

        val listWalletsInfo =
            (currentState.walletToFieldState as? WalletSuccessState)?.list.orEmpty()
                .plus((currentState.walletFromFieldState as? WalletSuccessState)?.list.orEmpty())

        // List of currency in selected wallets except selected currency in currency field
        val listWalletCurrencyExceptSelectedCurrency = listWalletsInfo
            .filter { walletIdList.contains(it.id) }
            .filter { it.currency != currentState.currencyFieldState.selectedCurrency }
            .map { it.currency }


        val finalTargetListCurrency =
            (categoryWithCurrencyDiffWithTransactionCurrency + listWalletCurrencyExceptSelectedCurrency)
                .filterSameCurrency()
                .takeIf { currentState.currencyFieldState.selectedCurrency != null }
                .orEmpty()

        state.update { iTransactionState ->
            iTransactionState.copyWithExchanges(
                exchangeFieldState = finalTargetListCurrency.map { targetCurrency ->
                    ExchangeFieldState(
                        baseCurrency = currentState.currencyFieldState.selectedCurrency!!,
                        targetCurrency = targetCurrency,
                        enteredAmount = TextFieldState(
                            text = getRateByStateAndCurrency(
                                currentState.currencyFieldState.selectedCurrency!!,
                                targetCurrency,
                            )
                        )
                    )
                }
            )
        }
    }

    private fun getRateByStateAndCurrency(
        baseCurrency: BudgetCurrencyEnum,
        targetCurrency: BudgetCurrencyEnum
    ): String {

        val rate = currencyRates?.map?.get(baseCurrency)
            ?.firstOrNull { rate -> rate.currency == targetCurrency }
            ?.rate ?: 0.0

        return roundToSixSignificantDigits(rate).toString()
    }

    private fun List<BudgetCurrencyEnum>.filterSameCurrency(): List<BudgetCurrencyEnum> =
        this
            .groupBy { it }
            .map { it.key }

    override fun setCategoriesIds(categoryPresentation: CategoryPresentation) {
        super.setCategoriesIds(categoryPresentation)
        showExchangesIfNeed()
    }

    override fun setCurrency(currency: BudgetCurrencyEnum) {
        super.setCurrency(currency)
        showExchangesIfNeed()
    }

    override fun setWalletId(fromId: String?, toId: String?) {
        super.setWalletId(fromId, toId)
        showExchangesIfNeed()
    }
}

fun roundToSixSignificantDigits(value: Double): Double {
    if (value == 0.0) {
        return 0.0
    }

    val magnitude = 10.0.pow(6 - value.toInt().toString().length)
    return Math.round(value * magnitude) / magnitude
}