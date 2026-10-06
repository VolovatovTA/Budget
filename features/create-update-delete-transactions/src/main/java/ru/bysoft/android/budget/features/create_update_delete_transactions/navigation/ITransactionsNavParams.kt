package ru.bysoft.android.budget.features.create_update_delete_transactions.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bysoft.android.budget.common.util.TransactionTypeEnum

@Serializable
data class TransactionsCreateNavParams(
    @SerialName("type")
    val type: TransactionTypeEnum,
)

@Serializable
data class TransactionUpdateNavParams(
    val id: String
)
