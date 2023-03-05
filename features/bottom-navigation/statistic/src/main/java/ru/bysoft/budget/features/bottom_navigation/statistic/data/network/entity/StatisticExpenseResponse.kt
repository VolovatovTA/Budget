package ru.bysoft.budget.features.bottom_navigation.statistic.data.network.entity

import com.google.gson.annotations.SerializedName

data class StatisticExpenseResponse(
    @SerializedName("data")
    val data: List<CategoryExpenseResponse>
)

data class CategoryExpenseResponse(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("icon_name")
    val iconName: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("limit_amount")
    val limitAmount: String?,
    @SerializedName("limit_type")
    val limitType: String?
)