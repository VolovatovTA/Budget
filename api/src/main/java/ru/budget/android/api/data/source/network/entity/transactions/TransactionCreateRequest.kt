package ru.budget.android.api.data.source.network.entity.transactions

import com.google.gson.annotations.SerializedName

sealed interface ITransactionCreateRequest

const val amountName = "amount"
const val commentName = "comment"
const val currencyName = "currency"
const val exchangesName = "exchanges"
const val expensesName = "expenses"
const val walletIdName = "wallet_id"
data class TransactionExpenseCreateRequest(
    @SerializedName(amountName)
    val amount: Float,
    @SerializedName(commentName)
    val comment: String,
    @SerializedName(currencyName)
    val currency: String,
    @SerializedName(exchangesName)
    val exchanges: List<Exchange>? = null,
    @SerializedName(expensesName)
    val expenses: List<Expense>? = null,
    @SerializedName(walletIdName)
    val walletId: String
) : ITransactionCreateRequest

data class TransactionIncomeCreateRequest(
    @SerializedName(amountName)
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
    @SerializedName(amountName)
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
    @SerializedName(amountName)
    val amount: Float,
    @SerializedName("currency")
    val currency: String
)

data class Expense(
    @SerializedName("id")
    val id: String
)