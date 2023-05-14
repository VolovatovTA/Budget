package ru.budget.android.api.data.source.network.entity.wallet

import com.google.gson.annotations.SerializedName

data class CreateWalletRequest(
    @SerializedName("icon_name")
    val iconName: String?,
    @SerializedName("balance")
    val balance: Float?,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("name")
    val name: String
)

data class UpdateWalletRequest(
    @SerializedName("icon_name")
    val iconName: String?,
    @SerializedName("name")
    val name: String
)