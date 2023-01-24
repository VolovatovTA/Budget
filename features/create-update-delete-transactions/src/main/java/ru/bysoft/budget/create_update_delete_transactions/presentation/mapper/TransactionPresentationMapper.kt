package ru.bysoft.budget.create_update_delete_transactions.presentation.mapper

import ru.bysoft.budget.create_update_delete_transactions.data.network.entity.requests.Exchange
import ru.bysoft.budget.create_update_delete_transactions.data.network.entity.requests.Expense
import ru.bysoft.budget.create_update_delete_transactions.data.network.entity.requests.TransactionCreateRequest
import ru.bysoft.budget.create_update_delete_transactions.presentation.entity.CategorySuccess
import ru.bysoft.budget.create_update_delete_transactions.presentation.entity.TransactionState
import ru.bysoft.budget.create_update_delete_transactions.presentation.entity.WalletSuccessState
import javax.inject.Inject

interface ITransactionPresentationMapper {
    fun toRequest(state: TransactionState): TransactionCreateRequest
}

class TransactionPresentationMapper @Inject constructor() : ITransactionPresentationMapper {
    override fun toRequest(state: TransactionState): TransactionCreateRequest {
        return TransactionCreateRequest(
            amount = state.amountState.text.toInt(),
            comment = state.commentState.text,
            currency = state.currencyFieldState.selectedCurrency?.iso4217 ?: "",
            expenses = (state.categoryState as CategorySuccess).listCategory
                .ifEmpty { null }
                ?.mapNotNull {
                    if (it.isChosen) Expense(it.id)
                    else null
                },
            walletId = (state.walletFieldState as WalletSuccessState).selectedWalletId ?: "",
            exchanges = state.exchangeFieldState.map {
                Exchange(
                    it.amount.text.toInt(),
                    it.currencyFieldState.selectedCurrency?.iso4217 ?: ""
                )
            }
        )
    }
}