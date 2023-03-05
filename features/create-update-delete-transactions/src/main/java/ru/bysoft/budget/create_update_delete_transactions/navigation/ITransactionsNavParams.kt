package ru.bysoft.budget.create_update_delete_transactions.navigation

import ru.bysoft.budget.create_update_delete_transactions.presentation.entity.TransactionTypeEnum

data class TransactionsCreateNavParams(
    val type: TransactionTypeEnum,
)

data class TransactionUpdateNavParams(
    val id: String
)
