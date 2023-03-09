package ru.bysoft.android.budget.features.create_update_delete_transactions.navigation

import ru.bysoft.android.budget.common.util.TransactionTypeEnum

data class TransactionsCreateNavParams(
    val type: TransactionTypeEnum,
)

data class TransactionUpdateNavParams(
    val id: String
)
