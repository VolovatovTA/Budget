package ru.bysoft.android.budget.features.bottom_navigation.statistic.data.network.entity

import com.google.gson.annotations.SerializedName

data class ListTransactionsResponse(
    @SerializedName("data")
    val data: List<TransactionResponse>
)

data class TransactionResponse(
    @SerializedName("amount")
    val amount: Float,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("exchanges")
    val exchanges: List<Exchange>,
)

data class Exchange(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("transaction_id")
    val transactionId: String,
    @SerializedName("updated_at")
    val updatedAt: String
)