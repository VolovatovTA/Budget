package ru.budget.android.api.data.source.network.entity.wallet

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateWalletRequest(
    @SerialName("icon_name")
    val iconName: String?,
    @SerialName("balance")
    val balance: Float?,
    @SerialName("currency")
    val currency: String,
    @SerialName("name")
    val name: String
)

@Serializable
data class UpdateWalletRequest(
    @SerialName("icon_name")
    val iconName: String?,
    @SerialName("name")
    val name: String
)