package ru.bysoft.budget.create_update_delete_transactions.data.network.entity.responses

import com.google.gson.annotations.SerializedName

data class TransactionsCategoryResponse(
    @SerializedName("data")
    val data: List<CategoryResponse>
)

data class CategoryResponse(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("icon_name")
    val iconName: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)