package ru.bysoft.budget.create_update_delete_transactions.navigation

import ru.bysoft.budget.common.util.TransactionTypeEnum

data class TransactionsCreateNavParams(
    val type: TransactionTypeEnum,
)

data class TransactionUpdateNavParams(
    val id: String
)
