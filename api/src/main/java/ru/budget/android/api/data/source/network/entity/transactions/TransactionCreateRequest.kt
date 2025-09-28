package ru.budget.android.api.data.source.network.entity.transactions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface ITransactionCreateRequest

const val amountName = "amount"
const val commentName = "comment"
const val currencyName = "currency"
const val exchangesName = "exchanges"
const val expensesName = "expenses"
const val walletIdName = "wallet_id"
@Serializable
data class TransactionExpenseCreateRequest(
    @SerialName(amountName)
    val amount: Float,
    @SerialName(commentName)
    val comment: String,
    @SerialName(currencyName)
    val currency: String,
    @SerialName(exchangesName)
    val exchanges: List<Exchange>? = null,
    @SerialName(expensesName)
    val expenses: List<Expense>? = null,
    @SerialName(walletIdName)
    val walletId: String
) : ITransactionCreateRequest

@Serializable
data class TransactionIncomeCreateRequest(
    @SerialName(amountName)
    val amount: Float,
    @SerialName("comment")
    val comment: String,
    @SerialName("currency")
    val currency: String,
    @SerialName("exchanges")
    val exchanges: List<Exchange>? = null,
    @SerialName("income_id")
    val income_id: String? = null,
    @SerialName("wallet_id")
    val walletId: String
) : ITransactionCreateRequest

@Serializable
data class TransactionTransferCreateRequest(
    @SerialName(amountName)
    val amount: Float,
    @SerialName("comment")
    val comment: String,
    @SerialName("currency")
    val currency: String,
    @SerialName("exchanges")
    val exchanges: List<Exchange>? = null,
    @SerialName("expenses")
    val expenses: List<Expense>? = null,
    @SerialName("expense_wallet_id")
    val walletIdFrom: String,
    @SerialName("income_wallet_id")
    val walletIdTo: String,
) : ITransactionCreateRequest

@Serializable
data class Exchange(
    @SerialName(amountName)
    val amount: Float,
    @SerialName("currency")
    val currency: String
)

@Serializable
data class Expense(
    @SerialName("id")
    val id: String
)