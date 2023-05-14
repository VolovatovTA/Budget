package ru.budget.android.api.data.source.network.entity.category

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("data")
    val data: List<CategoryItemResponse>
)

data class CategoryItemResponse(
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