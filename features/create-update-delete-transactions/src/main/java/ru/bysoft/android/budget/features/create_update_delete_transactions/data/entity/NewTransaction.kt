package ru.bysoft.android.budget.features.create_update_delete_transactions.data.entity

import ru.bysoft.android.budget.currency.BudgetCurrencyEnum

sealed interface NewTransaction {
    val amount: Float
    val comment: String
    val currency: BudgetCurrencyEnum
    val exchanges: List<NewExchange>

    data class Expense(
        override val amount: Float,
        override val comment: String,
        override val currency: BudgetCurrencyEnum,
        override val exchanges: List<NewExchange>,
        val categoryIds: List<String>,
        val walletId: String,
    ) : NewTransaction
    data class Income(
        override val amount: Float,
        override val comment: String,
        override val currency: BudgetCurrencyEnum,
        override val exchanges: List<NewExchange>,
        val categoryId: String?,
        val walletId: String,
    ) : NewTransaction
    data class Transfer(
        override val amount: Float,
        override val comment: String,
        override val currency: BudgetCurrencyEnum,
        override val exchanges: List<NewExchange>,
        val walletFromId: String,
        val walletToId: String,
    ) : NewTransaction
}

data class NewExchange(val amount: Float, val currency: BudgetCurrencyEnum)

class TransactionCreateException(
    message: String,
    val fieldErrors: Map<String, List<String>>,
) : Exception(message)
