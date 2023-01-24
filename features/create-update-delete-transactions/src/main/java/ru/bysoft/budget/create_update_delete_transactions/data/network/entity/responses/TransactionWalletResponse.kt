package ru.bysoft.budget.create_update_delete_transactions.data.network.entity.responses

import com.google.gson.annotations.SerializedName

data class TransactionWalletResponse(
    @SerializedName("data")
    val data: List<WalletResponse>
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