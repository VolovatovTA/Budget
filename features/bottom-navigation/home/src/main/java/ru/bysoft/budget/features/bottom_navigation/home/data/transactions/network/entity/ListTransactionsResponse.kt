package ru.bysoft.budget.features.bottom_navigation.home.data.transactions.network.entity

import com.google.gson.annotations.SerializedName

data class ListTransactionsResponse(
    @SerializedName("data")
    val data: List<TransactionResponse>
)

data class TransactionResponse(
    @SerializedName("amount")
    val amount: Float,
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("exchanges")
    val exchanges: List<Exchange>,
    @SerializedName("expenses")
    val listExpenseCategoryResponse: List<Expense>?,
    @SerializedName("id")
    val id: String,
    @SerializedName("income")
    val income: Income?,
    @SerializedName("transfer")
    val transfer: Transfer?,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("wallet")
    val wallet: Wallet
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

data class Expense(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("icon_name")
    val iconName: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: String
)

data class Income(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("icon_name")
    val iconName: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: String
)

data class Transfer(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("expense")
    val expense: Expense,
    @SerializedName("id")
    val id: String,
    @SerializedName("income")
    val income: Income,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class Wallet(
    @SerializedName("balance")
    val balance: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("icon_name")
    val iconName: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: String
)