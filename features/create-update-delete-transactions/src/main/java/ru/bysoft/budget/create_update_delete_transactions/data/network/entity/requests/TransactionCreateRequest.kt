package ru.bysoft.budget.create_update_delete_transactions.data.network.entity.requests

import com.google.gson.annotations.SerializedName

data class TransactionCreateRequest(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("exchanges")
    val exchanges: List<Exchange>? = null,
    @SerializedName("expenses")
    val expenses: List<Expense>? = null,
    @SerializedName("wallet_id")
    val walletId: String
)

data class Exchange(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("currency")
    val currency: String
)

data class Expense(
    @SerializedName("id")
    val id: String
)