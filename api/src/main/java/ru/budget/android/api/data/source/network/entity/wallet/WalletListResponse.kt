package ru.budget.android.api.data.source.network.entity.wallet

import com.google.gson.annotations.SerializedName

sealed interface WalletResponse

data class WalletListResponse(
    @SerializedName("data")
    val data: List<WalletItemResponse>
) : WalletResponse

data class WalletItemResponse(
    @SerializedName("balance")
    val balance: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("user_id")
    val userId: String?,
    @SerializedName("icon_name")
    val iconName: String?
) : WalletResponse

data class WalletErrorResponse(
    @SerializedName("slug")
    val slug: String
) : WalletResponse