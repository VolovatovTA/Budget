package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper

import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.entity.requests.*
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.*
import javax.inject.Inject

interface ITransactionPresentationMapper {
    fun toRequest(state: ITransactionState): ITransactionCreateRequest
}

class TransactionPresentationMapper @Inject constructor() :
    ITransactionPresentationMapper {
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
            exchanges = state.exchangeFieldState.map {
                Exchange(
                    (it.amount.text.toFloatOrNull() ?: 0f)*(state.amountState.text.toFloatOrNull() ?: 0f),
                    it.currencyFieldState.selectedCurrency?.iso4217 ?: ""
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
            exchanges = state.exchangeFieldState.map {
                Exchange(
                    (it.amount.text.toFloatOrNull() ?: 0f)*(state.amountState.text.toFloatOrNull() ?: 0f),
                    it.currencyFieldState.selectedCurrency?.iso4217 ?: ""
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
            exchanges = state.exchangeFieldState.map {
                Exchange(
                    (it.amount.text.toFloatOrNull() ?: 0f)*(state.amountState.text.toFloatOrNull() ?: 0f),
                    it.currencyFieldState.selectedCurrency?.iso4217 ?: ""
                )
            }
        )
}