package ru.bysoft.budget.create_update_delete_transactions.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.bysoft.budget.common.util.BudgetCurrency
import ru.bysoft.budget.create_update_delete_transactions.navigation.ITransactionNavigation
import ru.bysoft.budget.create_update_delete_transactions.presentation.entity.*

interface ITransactionsViewModel {
    val state: StateFlow<TransactionState>
    fun setWalletId(id: String)
    fun setAmount(amount: String)
    fun setCurrency(currency: BudgetCurrency)
    fun setCategoriesIds(categoryPresentation: CategoryPresentation)
    fun setComment(comment: String)
    fun setTypeTransactions(type: TransactionTypeEnum)
    fun setExchangeAmount(currency: BudgetCurrency, amount: String)
    fun setExchangeCurrency(position: Int, currency: BudgetCurrency)
    fun back()
}

interface ITransactionCreateViewModel : ITransactionsViewModel {
    fun create()
    fun initNavParams()
}

interface ITransactionUpdateViewModel : ITransactionsViewModel {
    fun update()
    fun initId(id: String)
}

interface ITransactionDeleteViewModel : ITransactionsViewModel {
    fun delete()
}

abstract class TransactionsCommonViewModel(
    private val navigate: ITransactionNavigation
) : ViewModel(), ITransactionsViewModel {


    override val state: MutableStateFlow<TransactionState> = MutableStateFlow(TransactionState())

    override fun setAmount(amount: String) {
        state.value = state.value.copy(
            amountState = state.value.amountState.copy(
                text = amount
            )
        )
    }

    override fun setComment(comment: String) {
        state.value = state.value.copy(
            commentState = state.value.commentState.copy(
                text = comment
            )
        )
    }

    override fun setTypeTransactions(type: TransactionTypeEnum) {
        state.value = state.value.copy(typeState = type)
    }

    override fun setCurrency(currency: BudgetCurrency) {
        state.update {
            it.copy(
                currencyFieldState = state.value.currencyFieldState.copy(
                    selectedCurrency = currency
                )
            )
        }
    }

    override fun setWalletId(id: String) {
        val currentWalletState = state.value.walletFieldState
        if (currentWalletState is WalletSuccessState) {
            state.update {
                it.copy(
                    walletFieldState = currentWalletState.copy(
                        selectedWalletId = id
                    )
                )
            }
        }
    }

    override fun setCategoriesIds(categoryPresentation: CategoryPresentation) {
        val currentCategoryState = state.value.categoryState
        if (currentCategoryState is CategorySuccess) {
            state.update { transactionState ->
                transactionState.copy(
                    categoryState = currentCategoryState.copy(
                        listCategory = currentCategoryState.listCategory.map { oldCategory ->
                            if (oldCategory == categoryPresentation) oldCategory.copy(isChosen = !oldCategory.isChosen)
                            else oldCategory
                        }
                    )
                )
            }
        }
    }

    override fun setExchangeAmount(currency: BudgetCurrency, amount: String) {
        state.update { st ->
            st.copy(
                exchangeFieldState = st.exchangeFieldState.map { ex ->
                    if (ex?.currencyFieldState?.selectedCurrency == currency) {
                        ex.copy(amount = ex.amount.copy(text = amount))
                    } else {
                        ex
                    }
                }
            )
        }
    }

    override fun setExchangeCurrency(position: Int, currency: BudgetCurrency) {
        state.update {
            it.copy(

            )
        }
    }

    override fun back() {
        navigate.back()
    }
}