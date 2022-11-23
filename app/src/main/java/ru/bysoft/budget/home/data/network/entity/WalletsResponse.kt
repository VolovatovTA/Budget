package ru.bysoft.budget.home.data.network.entity

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class WalletsResponse(
    @SerializedName("balance")
    val balance: Float?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("id")
    val id: UUID?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("user_id")
    val userId: UUID?
)