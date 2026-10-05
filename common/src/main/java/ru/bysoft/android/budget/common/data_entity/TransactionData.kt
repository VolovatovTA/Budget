package ru.bysoft.android.budget.common.data_entity

import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import java.util.Date

data class ListTransactionsData(
    val listTransactions: List<TransactionData>
)

sealed class TransactionData(
    open val id: String,
    open val comment: String?,
    open val date: Date?,
    open val categories: List<CategoryData>,
    open val amount: Float,
    open val currency: BudgetCurrencyEnum,
)

data class TransactionExpense(
    override val id: String,
    override val comment: String?,
    override val date: Date?,
    override val categories: List<CategoryData>,
    override val amount: Float,
    override val currency: BudgetCurrencyEnum,
) : TransactionData(id, comment, date, categories, amount, currency)

data class TransactionIncome(
    override val id: String,
    override val comment: String?,
    override val date: Date?,
    override val categories: List<CategoryData>,
    override val amount: Float,
    override val currency: BudgetCurrencyEnum,
) : TransactionData(id, comment, date, categories, amount, currency)

data class TransactionTransfer(
    override val id: String,
    override val comment: String?,
    override val date: Date?,
    override val amount: Float,
    override val currency: BudgetCurrencyEnum,
) : TransactionData(id, comment, date, emptyList(), amount, currency)