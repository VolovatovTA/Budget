package ru.bysoft.android.budget.features.create_update_wallet.data.network.entity
import com.google.gson.annotations.SerializedName

data class CreateWalletRequest(
    @SerializedName("balance")
    val balance: Double,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("name")
    val name: String
)