package ru.budget.android.api.data.source.network.entity.transactions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.budget.android.api.data.source.network.entity.wallet.WalletItemResponse
import ru.budget.android.api.data.source.network.entity.wallet.WalletResponse


@kotlinx.serialization.Serializable
data class TransactionResponse(
    @SerialName("data")
    val data: List<TransactionItemResponse?>?
)

@kotlinx.serialization.Serializable
data class TransactionItemResponse(
    @SerialName("amount")
    val amount: String,
    @SerialName("comment")
    val comment: String?,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("currency")
    val currency: String,
    @SerialName("exchanges")
    val exchanges: List<TransactionExchangeResponse>,
    @SerialName("expenses")
    val listTransactionExpenseResponse: List<TransactionExpenseResponse>?,
    @SerialName("id")
    val id: String,
    @SerialName("income")
    val transactionIncomeResponse: TransactionIncomeResponse?,
    @SerialName("transfer")
    val transfer: TransactionTransferResponse?,
    @SerialName("type")
    val type: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("wallet")
    val transactionWalletResponse: WalletItemResponse
)

@kotlinx.serialization.Serializable
data class TransactionExchangeResponse(
    @SerialName("amount")
    val amount: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("currency")
    val currency: String,
    @SerialName("id")
    val id: String,
    @SerialName("transaction_id")
    val transactionId: String,
    @SerialName("updated_at")
    val updatedAt: String
)

@kotlinx.serialization.Serializable
data class TransactionExpenseResponse(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("currency")
    val currency: String,
    @SerialName("icon_name")
    val iconName: String,
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("wallet")
    val wallet: WalletItemResponse
)

@kotlinx.serialization.Serializable
data class TransactionIncomeResponse(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("currency")
    val currency: String,
    @SerialName("icon_name")
    val iconName: String,
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("wallet")
    val wallet: WalletItemResponse
)

@Serializable
data class TransactionTransferResponse(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("expense")
    val expense: TransactionExpenseResponse,
    @SerialName("id")
    val id: String,
    @SerialName("income")
    val income: TransactionIncomeResponse,
    @SerialName("updated_at")
    val updatedAt: String
)