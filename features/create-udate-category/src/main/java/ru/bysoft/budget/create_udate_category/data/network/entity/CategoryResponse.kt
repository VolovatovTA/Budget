package ru.bysoft.budget.create_udate_category.data.network.entity

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("icon_name")
    val iconName: String? = null,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
)