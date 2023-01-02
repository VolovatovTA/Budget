package ru.bysoft.budget.features.bottom_navigation.home.data.transactions.network.entity

import com.google.gson.annotations.SerializedName

data class ListTransactionsResponse(
    @SerializedName("data")
    val data: List<TransactionResponse>,
    @SerializedName("filter")
    val filter: Filter
)

data class TransactionResponse(
    @SerializedName("amount")
    val amount: Float,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("created")
    val created: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("expenses")
    val listExpenseCategoryResponse: List<CategoryResponse>?,
    @SerializedName("id")
    val id: String,
    @SerializedName("income")
    val listIncomeCategoryResponse: CategoryResponse?,
    @SerializedName("type")
    val type: String,
    @SerializedName("wallet")
    val wallet: TransactionWalletResponse
)

data class CategoryResponse(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)

data class TransactionWalletResponse(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)