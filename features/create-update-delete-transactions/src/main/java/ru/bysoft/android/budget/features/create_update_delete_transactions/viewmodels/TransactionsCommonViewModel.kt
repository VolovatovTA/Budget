package ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.budget.android.api.data.source.network.ICategoryApi
import ru.budget.android.api.data.source.network.IWalletApi
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.common.util.CategoryTypeEnum
import ru.bysoft.android.budget.common.util.TransactionTypeEnum
import ru.bysoft.android.budget.currency.BudgetCurrency
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.ITransactionNavigation
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.TransactionsCreateNavParams
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.*
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionWalletPresentationMapper
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionsCategoryPresentationMapper

interface ITransactionsViewModel {
    val toastState: MutableSharedFlow<Int>
    val state: StateFlow<ITransactionState>
    fun setWalletId(fromId: String?, toId: String?)
    fun setAmount(amount: String)
    fun setCurrency(currency: BudgetCurrency)
    fun setCategoriesIds(categoryPresentation: CategoryPresentation)
    fun setComment(comment: String)
    fun setTypeTransactions(type: TransactionTypeEnum)
    fun onEmptyCategoryClick()
    fun setFullAmount(currency: BudgetCurrency, newValue: Boolean)
    fun setRevert(currency: BudgetCurrency, newValueIsRevert: Boolean)
    fun setExchangeAmount(currency: BudgetCurrency, amount: String)
    fun back()
}

interface ITransactionCreateViewModel : ITransactionsViewModel {
    fun create()
    fun initNavParams(argument: TransactionsCreateNavParams)
}

interface ITransactionUpdateViewModel : ITransactionsViewModel {
    fun update()
    fun initId(id: String)
}

abstract class TransactionsCommonViewModel(
    private val navigate: ITransactionNavigation,
    private val errorLogger: IErrorLogger,
    private val categoryApi: ICategoryApi,
    private val walletApi: IWalletApi,
    private val categoryMapperPresentation: ITransactionsCategoryPresentationMapper,
    private val walletMapper: ITransactionWalletPresentationMapper,
) : ViewModel(), ITransactionsViewModel {


    override val toastState = MutableSharedFlow<Int>()

    private val handlerCategory = CoroutineExceptionHandler { _, t ->
        errorLogger.logError(t)
        val currentState = state.value
        if (currentState is TransactionExpenseState) {
            state.update { currentState.copy(categoryState = CategoryError) }
        }
        if (currentState is TransactionIncomeState) {
            state.update { currentState.copy(categoryState = CategoryError) }
        }
    }

    private val handlerWallet = CoroutineExceptionHandler { _, t ->
        errorLogger.logError(t)
        val currentState = state.value
        state.update {
            currentState.copyWithWalletFromState(walletFieldState = WalletErrorState)
                .copyWithWalletToState(walletFieldState = WalletErrorState)
        }
    }

    override val state: MutableStateFlow<ITransactionState> =
        MutableStateFlow(TransactionExpenseState())

    override fun setAmount(amount: String) {
        val previousAmount = state.value.amountState.text.toDoubleOrNull() ?: 0.0
        state.update {
            it.copyWithAmount(
                amountState = it.amountState.copy(
                    text = amount
                )
            )
        }

        state.value.exchangeFieldState.forEach { currentExchangeFieldState ->
            if (currentExchangeFieldState.isFullAmount) {
                val exchangeAmount =
                    currentExchangeFieldState.enteredAmount.text.toDoubleOrNull() ?: 0.0
                val multiplier =
                    if (currentExchangeFieldState.isRevert) state.value.amountState.text.toDoubleOrNull()
                        ?: Double.MAX_VALUE else state.value.amountState.text.toDoubleOrNull()
                        ?: 0.0
                val newAmount = exchangeAmount / previousAmount * multiplier
                setExchangeAmount(
                    currentExchangeFieldState.targetCurrency,
                    roundToSixSignificantDigits(newAmount).toString()
                )
                state.update {
                    it.copyWithExchanges(
                        exchangeFieldState = it.exchangeFieldState.map { exchange ->
                            if (exchange.targetCurrency == currentExchangeFieldState.targetCurrency) {
                                exchange.copy(
                                    shownAmount = amount.toDoubleOrNull() ?: 0.0,
                                )
                            } else exchange
                        }
                    )
                }
            }
        }
    }

    override fun setComment(comment: String) {
        state.value = state.value.copyWithComment(
            commentState = state.value.commentState.copy(
                text = comment
            )
        )
    }

    override fun onEmptyCategoryClick() {
        when (state.value) {
            is TransactionExpenseState -> navigate.toCreateCategoryExpense()
            is TransactionIncomeState -> navigate.toCreateCategoryIncome()
            is TransactionTransferState -> {}
        }
    }

    override fun setTypeTransactions(type: TransactionTypeEnum) {
        state.update {
            when (type) {
                TransactionTypeEnum.TRANSFER -> TransactionTransferState(
                    isLoading = false,
                    currencyFieldState = state.value.currencyFieldState,
                    amountState = state.value.amountState,
                    commentState = state.value.commentState,
                )
                TransactionTypeEnum.INCOME -> TransactionIncomeState(
                    isLoading = false,
                    currencyFieldState = state.value.currencyFieldState,
                    amountState = state.value.amountState,
                    commentState = state.value.commentState,
                )
                TransactionTypeEnum.EXPENSE -> TransactionExpenseState(
                    isLoading = false,
                    currencyFieldState = state.value.currencyFieldState,
                    amountState = state.value.amountState,
                    commentState = state.value.commentState,
                )
            }
        }
        if (state.value !is TransactionTransferState) loadCategories()
        loadWallets()
    }

    override fun setCurrency(currency: BudgetCurrency) {
        state.update {
            it.copyWithCurrency(
                currencyFieldState = state.value.currencyFieldState.copy(
                    selectedCurrency = currency
                )
            )
        }
    }

    override fun setWalletId(fromId: String?, toId: String?) {
        when {
            toId != null -> {
                val currentWalletToState = state.value.walletToFieldState
                if (currentWalletToState is WalletSuccessState) {
                    state.value = state.value.copyWithWalletToState(
                        walletFieldState = currentWalletToState.copy(
                            selectedWalletId = toId
                        )
                    )
                }
            }
            fromId != null -> {
                val currentWalletFromState = state.value.walletFromFieldState
                if (currentWalletFromState is WalletSuccessState) {
                    state.value = state.value.copyWithWalletFromState(
                        walletFieldState = currentWalletFromState.copy(
                            selectedWalletId = fromId
                        )
                    )
                }
            }
        }
    }

    override fun setCategoriesIds(categoryPresentation: CategoryPresentation) {
        val currentState = state.value
        if (currentState is TransactionExpenseState) {
            updateCategoriesId(currentState, categoryPresentation)
        } else if (currentState is TransactionIncomeState) {
            updateCategoriesId(currentState, categoryPresentation)
        }

    }

    private fun updateCategoriesId(
        currentState: TransactionExpenseState,
        categoryPresentation: CategoryPresentation
    ) {
        val currentCategoryState = state.value.categoryState
        if (currentCategoryState is CategorySuccess) {
            state.value = currentState.copy(
                categoryState = currentCategoryState.copy(
                    listCategory = currentCategoryState.listCategory.map { oldCategory ->
                        if (oldCategory == categoryPresentation) oldCategory.copy(isChosen = !oldCategory.isChosen)
                        else oldCategory
                    }
                )
            )
        }
    }

    private fun updateCategoriesId(
        currentState: TransactionIncomeState,
        categoryPresentation: CategoryPresentation
    ) {
        val currentCategoryState = state.value.categoryState
        if (currentCategoryState is CategorySuccess) {
            state.value = currentState.copy(
                categoryState = currentCategoryState.copy(
                    listCategory = currentCategoryState.listCategory.map { oldCategory ->
                        if (oldCategory == categoryPresentation) oldCategory.copy(isChosen = !oldCategory.isChosen)
                        else oldCategory.copy(isChosen = false)
                    }
                )
            )
        }
    }

    override fun setExchangeAmount(currency: BudgetCurrency, amount: String) {
        state.update { transactionState ->
            transactionState.copyWithExchanges(
                exchangeFieldState = transactionState.exchangeFieldState.map { ex ->
                    if (ex.targetCurrency == currency) {
                        ex.copy(enteredAmount = ex.enteredAmount.copy(text = amount))
                    } else {
                        ex
                    }
                }
            )
        }
    }

    override fun setFullAmount(currency: BudgetCurrency, newValue: Boolean) {
        state.update { transactionState ->
            transactionState.copyWithExchanges(
                exchangeFieldState = transactionState.exchangeFieldState.map { ex ->
                    if (ex.targetCurrency == currency) {
                        val currentMainAmount = state.value.amountState.text.toDoubleOrNull()
                            ?: if (newValue) 0.0 else Double.MAX_VALUE

                        val shownAmount = if (ex.isRevert) ex.shownAmount else currentMainAmount
                        val multipliedEnteredAmount = ex.enteredAmount.text.toDoubleOrNull()
                            ?.times(if (newValue) shownAmount else 1 / shownAmount)
                            ?: 0.0
                        ex.copy(
                            isFullAmount = newValue,
                            enteredAmount = ex.enteredAmount.copy(
                                text = roundToSixSignificantDigits(multipliedEnteredAmount).toString()
                            ),
                            shownAmount = if (newValue) shownAmount else 1.0
                        )
                    } else {
                        ex
                    }
                }
            )
        }
    }

    override fun setRevert(currency: BudgetCurrency, newValueIsRevert: Boolean) {
        state.update { transactionState ->
            transactionState.copyWithExchanges(
                exchangeFieldState = transactionState.exchangeFieldState.map { ex ->
                    if (ex.targetCurrency == currency) {
                        val shownAmount =
                            if (ex.isFullAmount) {
                                state.value.amountState.text.toDoubleOrNull() ?: 0.0
                            } else {
                                1.0
                            }
                        val enteredAmount =
                            roundToSixSignificantDigits(
                                shownAmount /
                                        (ex.enteredAmount.text.toDoubleOrNull() ?: Double.MAX_VALUE)

                            )
                        ex.copy(
                            isRevert = newValueIsRevert,
                            enteredAmount = ex.enteredAmount.copy(
                                text = enteredAmount.toString()
                            ),
                            shownAmount = shownAmount
                        )
                    } else {
                        ex
                    }
                }
            )
        }
    }

    override fun back() {
        navigate.back()
    }


    private fun loadCategories() {
        viewModelScope.launch(handlerCategory) {
            state.update {
                it
                    .copyWithCategory(CategoryWaiting)
            }

            val neededCategoryType = when (state.value) {
                is TransactionExpenseState -> CategoryTypeEnum.EXPENSE
                is TransactionIncomeState -> CategoryTypeEnum.INCOME
                is TransactionTransferState -> return@launch
            }

            val response = categoryApi.getCategories(neededCategoryType.pathToBack)
            state.update {
                it
                    .copyWithCategory(categoryMapperPresentation.toPresentation(response))
            }
        }
    }

    private fun loadWallets() {
        viewModelScope.launch(handlerWallet) {
            state.update {
                it.copyWithWalletFromState(
                    walletFieldState = WalletWaitingState
                )
            }
            val response = walletApi.getWallets()
            state.update { iTransactionState ->
                when (iTransactionState) {
                    is TransactionTransferState -> {
                        iTransactionState.copyWithWalletFromState(
                            walletFieldState = walletMapper.toPresentation(response)
                        ).copyWithWalletToState(
                            walletFieldState = walletMapper.toPresentation(response)
                        )
                    }
                    is TransactionIncomeState -> {
                        iTransactionState.copyWithWalletToState(
                            walletFieldState = walletMapper.toPresentation(response)
                        )
                    }
                    is TransactionExpenseState -> {
                        iTransactionState.copyWithWalletFromState(
                            walletFieldState = walletMapper.toPresentation(response)
                        )
                    }
                }
            }
        }
    }
}