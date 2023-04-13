package ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.common.util.BudgetCurrency
import ru.bysoft.android.budget.common.util.TransactionTypeEnum
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.ITransactionsCategoryApi
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.ITransactionsWalletApi
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.ITransactionNavigation
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.TransactionsCreateNavParams
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.*
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionWalletPresentationMapper
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.ITransactionsCategoryPresentationMapper

interface ITransactionsViewModel {
    val state: StateFlow<ITransactionState>
    fun setWalletId(fromId: String?, toId: String?)
    fun setAmount(amount: String)
    fun setCurrency(currency: BudgetCurrency)
    fun setCategoriesIds(categoryPresentation: CategoryPresentation)
    fun setComment(comment: String)
    fun setTypeTransactions(type: TransactionTypeEnum)
    fun onEmptyCategoryClick()
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
    private val categoryApi: ITransactionsCategoryApi,
    private val walletApi: ITransactionsWalletApi,
    private val categoryMapperPresentation: ITransactionsCategoryPresentationMapper,
    private val walletMapper: ITransactionWalletPresentationMapper,
) : ViewModel(), ITransactionsViewModel {

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
        state.update {
            it.copyWithAmount(
                amountState = it.amountState.copy(
                    text = amount
                )
            )
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
                TransactionTypeEnum.TRANSFER -> TransactionTransferState()
                TransactionTypeEnum.INCOME -> TransactionIncomeState()
                TransactionTypeEnum.EXPENSE -> TransactionExpenseState()
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
                    if (ex.currencyFieldState.selectedCurrency == currency) {
                        ex.copy(amount = ex.amount.copy(text = amount))
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
                    .copyWithLoading(true)
            }

            val path = when (state.value) {
                is TransactionExpenseState -> "expenses"
                is TransactionTransferState -> "transfer"
                is TransactionIncomeState -> "incomes"
            }

            val response = categoryApi.getCategories(path)
            state.update {
                it
                    .copyWithCategory(categoryMapperPresentation.toPresentation(response))
                    .copyWithLoading(false)
            }
        }
    }

    private fun loadWallets() {
        viewModelScope.launch(handlerWallet) {
            state.update {
                it.copyWithWalletFromState(
                    walletFieldState = WalletWaitingState
                ).copyWithLoading(true)
            }
            val response = walletApi.getWallets()
            state.update { iTransactionState ->
                when (iTransactionState) {
                    is TransactionTransferState -> {
                        iTransactionState.copyWithWalletFromState(
                            walletFieldState = walletMapper.toPresentation(response)
                        ).copyWithWalletToState(
                            walletFieldState = walletMapper.toPresentation(response)
                        ).copyWithLoading(false)
                    }
                    is TransactionIncomeState -> {
                        iTransactionState.copyWithWalletToState(
                            walletFieldState = walletMapper.toPresentation(response)
                        ).copyWithLoading(false)
                    }
                    is TransactionExpenseState -> {
                        iTransactionState.copyWithWalletFromState(
                            walletFieldState = walletMapper.toPresentation(response)
                        ).copyWithLoading(false)
                    }
                }
            }
        }
    }
}