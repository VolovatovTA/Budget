package ru.bysoft.budget.create_update_delete_transactions.navigation

sealed interface ITransactionsNavParams

object TransactionNavParamsCreate : ITransactionsNavParams
data class TransactionNavParamsUpdate(
    val id: String
) : ITransactionsNavParams
data class TransactionNavParamsRead(
    val id: String
) : ITransactionsNavParams
