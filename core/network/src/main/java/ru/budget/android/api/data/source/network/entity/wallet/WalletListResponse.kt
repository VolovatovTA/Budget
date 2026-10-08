package ru.budget.android.api.data.source.network.entity.wallet

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface WalletResponse

@kotlinx.serialization.Serializable
data class WalletListResponse(
    @SerialName("data")
    val data: List<WalletItemResponse>
) : WalletResponse

@kotlinx.serialization.Serializable
data class WalletItemResponse(
    @SerialName("balance")
    val balance: String?,
    @SerialName("currency")
    val currency: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("user_id")
    val userId: String?,
    @SerialName("icon_name")
    val iconName: String?
) : WalletResponse

@Serializable
data class WalletErrorResponse(
    @SerialName("slug")
    val slug: String
) : WalletResponse