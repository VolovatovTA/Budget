package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper

import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.entity.NewExchange
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.entity.NewTransaction
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.CategorySuccess
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.ExchangeFieldState
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.ITransactionState
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.TransactionExpenseState
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.TransactionIncomeState
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.TransactionTransferState
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.WalletSuccessState

interface ITransactionPresentationMapper {
    fun toNewTransaction(state: ITransactionState): NewTransaction
}

class TransactionPresentationMapper : ITransactionPresentationMapper {

    override fun toNewTransaction(state: ITransactionState): NewTransaction = when (state) {
        is TransactionExpenseState -> NewTransaction.Expense(
            amount = state.amount,
            comment = state.commentState.text,
            currency = state.currencyOrUnknown,
            exchanges = state.exchanges(),
            categoryIds = (state.categoryState as? CategorySuccess)?.listCategory
                ?.filter { it.isChosen }?.map { it.id }.orEmpty(),
            walletId = (state.walletFromFieldState as? WalletSuccessState)?.selectedWalletId ?: "",
        )
        is TransactionIncomeState -> NewTransaction.Income(
            amount = state.amount,
            comment = state.commentState.text,
            currency = state.currencyOrUnknown,
            exchanges = state.exchanges(),
            categoryId = (state.categoryState as CategorySuccess).listCategory.firstOrNull { it.isChosen }?.id,
            walletId = (state.walletToFieldState as? WalletSuccessState)?.selectedWalletId ?: "",
        )
        is TransactionTransferState -> NewTransaction.Transfer(
            amount = state.amount,
            comment = state.commentState.text,
            currency = state.currencyOrUnknown,
            exchanges = state.exchanges(),
            walletFromId = (state.walletFromFieldState as? WalletSuccessState)?.selectedWalletId ?: "",
            walletToId = (state.walletToFieldState as? WalletSuccessState)?.selectedWalletId ?: "",
        )
    }

    private val ITransactionState.amount: Float
        get() = amountState.text.toFloatOrNull() ?: 0f

    private val ITransactionState.currencyOrUnknown: BudgetCurrencyEnum
        get() = currencyFieldState.selectedCurrency ?: BudgetCurrencyEnum.UNKNOWN

    private fun ITransactionState.exchanges(): List<NewExchange> = exchangeFieldState.map { field ->
        NewExchange(amount = field.amountInTargetCurrency(amount), currency = field.targetCurrency)
    }

    private fun ExchangeFieldState.amountInTargetCurrency(baseAmount: Float): Float {
        val entered = enteredAmount.text.toFloatOrNull()
        val rate = if (isRevert) 1 / (entered ?: Float.MAX_VALUE) else entered ?: 0f
        return if (isFullAmount) rate.takeIf { it != Float.MAX_VALUE } ?: 0f else baseAmount * rate
    }
}
