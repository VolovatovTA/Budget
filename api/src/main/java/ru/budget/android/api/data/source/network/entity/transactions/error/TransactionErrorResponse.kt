package ru.budget.android.api.data.source.network.entity.transactions.error

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TransactionErrorResponse(
    @SerialName("errors")
    val errors: Map<String, List<String>>,
    @SerialName("message")
    val message: String
)