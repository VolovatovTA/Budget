package ru.budget.android.api.data.source.network.entity.transactions

import com.google.gson.annotations.SerializedName
import ru.budget.android.api.data.source.network.entity.wallet.WalletItemResponse
import ru.budget.android.api.data.source.network.entity.wallet.WalletResponse

data class TransactionResponse(
    @SerializedName("data")
    val data: List<TransactionItemResponse?>?
)

data class TransactionItemResponse(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("exchanges")
    val exchanges: List<TransactionExchangeResponse>,
    @SerializedName("expenses")
    val listTransactionExpenseResponse: List<TransactionExpenseResponse>?,
    @SerializedName("id")
    val id: String,
    @SerializedName("income")
    val transactionIncomeResponse: TransactionIncomeResponse?,
    @SerializedName("transfer")
    val transfer: TransactionTransferResponse?,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("wallet")
    val transactionWalletResponse: WalletItemResponse
)

data class TransactionExchangeResponse(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("transaction_id")
    val transactionId: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class TransactionExpenseResponse(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("icon_name")
    val iconName: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("wallet")
    val wallet: WalletItemResponse
)

data class TransactionIncomeResponse(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("icon_name")
    val iconName: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("wallet")
    val wallet: WalletItemResponse
)

data class TransactionTransferResponse(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("expense")
    val expense: TransactionExpenseResponse,
    @SerializedName("id")
    val id: String,
    @SerializedName("income")
    val income: TransactionIncomeResponse,
    @SerializedName("updated_at")
    val updatedAt: String
)