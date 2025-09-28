package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper

import ru.budget.android.api.data.source.network.entity.transactions.*
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.*

interface ITransactionPresentationMapper {
    fun toRequest(state: ITransactionState): ITransactionCreateRequest
}

class TransactionPresentationMapper(

) : ITransactionPresentationMapper {
    override fun toRequest(state: ITransactionState): ITransactionCreateRequest {
        return when (state) {
            is TransactionIncomeState -> transactionExpenseIncomeCreateRequest(state)
            is TransactionExpenseState -> transactionExpenseIncomeCreateRequest(state)
            is TransactionTransferState -> transactionTransferCreateRequest(state)
        }
    }

    private fun transactionExpenseIncomeCreateRequest(state: TransactionIncomeState) =
        TransactionIncomeCreateRequest(
            amount = state.amountState.text.toFloatOrNull() ?: 0f,
            comment = state.commentState.text,
            currency = state.currencyFieldState.selectedCurrency?.iso4217 ?: "",
            income_id = (state.categoryState as CategorySuccess).listCategory
                .firstOrNull { it.isChosen }
                ?.id,
            walletId = (state.walletToFieldState as? WalletSuccessState)?.selectedWalletId ?: "",
            exchanges = state.exchangeFieldState.map { exchangeFieldState ->
                val enteredAmount = if (exchangeFieldState.isRevert) 1 / (exchangeFieldState.enteredAmount.text.toFloatOrNull()
                    ?: Float.MAX_VALUE) else exchangeFieldState.enteredAmount.text.toFloatOrNull() ?: 0f
                Exchange(
                    if (exchangeFieldState.isFullAmount) {
                        enteredAmount.takeIf { it != Float.MAX_VALUE } ?: 0f
                    } else {
                        (state.amountState.text.toFloatOrNull()
                            ?: 0f) * (enteredAmount)
                    },
                    exchangeFieldState.targetCurrency.iso4217
                )
            }
        )

    private fun transactionExpenseIncomeCreateRequest(state: TransactionExpenseState) =
        TransactionExpenseCreateRequest(
            amount = state.amountState.text.toFloatOrNull() ?: 0f,
            comment = state.commentState.text,
            currency = state.currencyFieldState.selectedCurrency?.iso4217 ?: "",
            expenses = (state.categoryState as? CategorySuccess)?.listCategory
                ?.mapNotNull {
                    if (it.isChosen) Expense(it.id)
                    else null
                }?.ifEmpty { null },
            walletId = (state.walletFromFieldState as? WalletSuccessState)?.selectedWalletId ?: "",
            exchanges = state.exchangeFieldState.map { exchangeFieldState ->
                val enteredAmount = if (exchangeFieldState.isRevert) 1 / (exchangeFieldState.enteredAmount.text.toFloatOrNull()
                    ?: Float.MAX_VALUE) else exchangeFieldState.enteredAmount.text.toFloatOrNull() ?: 0f
                Exchange(
                    if (exchangeFieldState.isFullAmount) {
                        enteredAmount.takeIf { it != Float.MAX_VALUE } ?: 0f
                    } else {
                        (state.amountState.text.toFloatOrNull()
                            ?: 0f) * (enteredAmount)
                    },
                    exchangeFieldState.targetCurrency.iso4217
                )
            }
        )

    private fun transactionTransferCreateRequest(state: TransactionTransferState) =
        TransactionTransferCreateRequest(
            amount = state.amountState.text.toFloatOrNull() ?: 0f,
            comment = state.commentState.text,
            currency = state.currencyFieldState.selectedCurrency?.iso4217 ?: "",
            expenses = null,
            walletIdTo = (state.walletToFieldState as? WalletSuccessState)?.selectedWalletId ?: "",
            walletIdFrom = (state.walletFromFieldState as? WalletSuccessState)?.selectedWalletId
                ?: "",
            exchanges = state.exchangeFieldState.map { exchangeFieldState ->
                val enteredAmount = if (exchangeFieldState.isRevert) 1 / (exchangeFieldState.enteredAmount.text.toFloatOrNull()
                    ?: Float.MAX_VALUE) else exchangeFieldState.enteredAmount.text.toFloatOrNull() ?: 0f
                Exchange(
                    if (exchangeFieldState.isFullAmount) {
                        enteredAmount.takeIf { it != Float.MAX_VALUE } ?: 0f
                    } else {
                        (state.amountState.text.toFloatOrNull()
                            ?: 0f) * (enteredAmount)
                    },
                    exchangeFieldState.targetCurrency.iso4217
                )
            }
        )
}