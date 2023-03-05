package ru.bysoft.budget.create_update_delete_transactions.data.network.entity.requests

import com.google.gson.annotations.SerializedName

sealed interface ITransactionCreateRequest

data class TransactionExpenseCreateRequest(
    @SerializedName("amount")
    val amount: Float,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("exchanges")
    val exchanges: List<Exchange>? = null,
    @SerializedName("expenses")
    val expenses: List<Expense>? = null,
    @SerializedName("wallet_id")
    val walletId: String
) : ITransactionCreateRequest

data class TransactionIncomeCreateRequest(
    @SerializedName("amount")
    val amount: Float,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("exchanges")
    val exchanges: List<Exchange>? = null,
    @SerializedName("income_id")
    val income_id: String? = null,
    @SerializedName("wallet_id")
    val walletId: String
) : ITransactionCreateRequest

data class TransactionTransferCreateRequest(
    @SerializedName("amount")
    val amount: Float,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("exchanges")
    val exchanges: List<Exchange>? = null,
    @SerializedName("expenses")
    val expenses: List<Expense>? = null,
    @SerializedName("expense_wallet_id")
    val walletIdFrom: String,
    @SerializedName("income_wallet_id")
    val walletIdTo: String,
) : ITransactionCreateRequest

data class Exchange(
    @SerializedName("amount")
    val amount: Float,
    @SerializedName("currency")
    val currency: String
)

data class Expense(
    @SerializedName("id")
    val id: String
)