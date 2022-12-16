package ru.bysoft.budget.features.create_wallet.data.network.entity
import com.google.gson.annotations.SerializedName

sealed interface CreateWalletResponse

data class CreateWalletSuccessResponse(
    @SerializedName("balance")
    val balance: Double,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("user_id")
    val userId: String
): CreateWalletResponse

data class CreateWalletErrorResponse(
    @SerializedName("slug")
    val slug: String
): CreateWalletResponse