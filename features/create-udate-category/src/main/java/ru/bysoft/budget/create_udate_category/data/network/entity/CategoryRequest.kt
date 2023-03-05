package ru.bysoft.budget.create_udate_category.data.network.entity

import com.google.gson.annotations.SerializedName

data class CategoryRequest(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("icon_name")
    val iconName: String? = null,
    @SerializedName("name")
    val name: String,
    @SerializedName("limit_type")
    val limitType: String?,
    @SerializedName("limit_amount")
    val limitAmount: Float?
)
