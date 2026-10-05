package ru.budget.android.api.data.source.network.entity.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("data")
    val data: List<CategoryItemResponse>
)

@Serializable
data class CategoryItemResponse(
    @SerialName("currency")
    val currency: String,
    @SerialName("icon_name")
    val iconName: String?,
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("limit_amount")
    val limitAmount: String?,
    @SerialName("limit_type")
    val limitType: String?
)