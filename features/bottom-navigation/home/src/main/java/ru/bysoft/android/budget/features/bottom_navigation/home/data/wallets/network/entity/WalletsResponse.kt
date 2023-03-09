package ru.bysoft.android.budget.features.bottom_navigation.home.data.wallets.network.entity

import com.google.gson.annotations.SerializedName

data class WalletsResponse(
    @SerializedName("data")
    val data: List<WalletResponse>?
)

data class WalletResponse(
    @SerializedName("balance")
    val balance: Float?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("user_id")
    val userId: String?
)