package ru.bysoft.budget.features.bottom_navigation.home.data.transactions.network.entity

import com.google.gson.annotations.SerializedName

data class TransactionsRequest(
    @SerializedName("filter")
    val filter: Filter
)

data class Filter(
    @SerializedName("count")
    val count: Int,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("date_from")
    val dateFrom: String?,
    @SerializedName("date_to")
    val dateTo: String?,
    @SerializedName("expense_ids")
    val expenseIds: List<String>?,
    @SerializedName("income_ids")
    val incomeIds: List<String>?,
    @SerializedName("last_id")
    val lastId: String?,
    @SerializedName("sort_by")
    val sortBy: String,
    @SerializedName("type")
    val type: List<String>?,
    @SerializedName("wallet_ids")
    val walletIds: List<String>?
)