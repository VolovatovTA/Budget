package ru.budget.android.api.data.source.network.entity.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryRequest(
    @SerialName("currency")
    val currency: String,
    @SerialName("icon_name")
    val iconName: String? = null,
    @SerialName("name")
    val name: String,
    @SerialName("limit_type")
    val limitType: String?,
    @SerialName("limit_amount")
    val limitAmount: Float?
)
