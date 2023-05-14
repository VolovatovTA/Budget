package ru.budget.android.api.data.source.network.entity.transactions.error

import com.google.gson.annotations.SerializedName

data class TransactionErrorResponse(
    @SerializedName("errors")
    val errors: Map<String, List<String>>,
    @SerializedName("message")
    val message: String
)