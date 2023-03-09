package ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions.entity

import ru.bysoft.android.budget.common.util.BudgetCurrency
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
    open val currency: BudgetCurrency,
)

data class TransactionExpense(
    override val id: String,
    override val comment: String?,
    override val date: Date?,
    override val categories: List<CategoryData>,
    override val amount: Float,
    override val currency: BudgetCurrency,
) : TransactionData(id, comment, date, categories, amount, currency)

data class TransactionIncome(
    override val id: String,
    override val comment: String?,
    override val date: Date?,
    override val categories: List<CategoryData>,
    override val amount: Float,
    override val currency: BudgetCurrency,
) : TransactionData(id, comment, date, categories, amount, currency)

data class TransactionTransfer(
    override val id: String,
    override val comment: String?,
    override val date: Date?,
    override val amount: Float,
    override val currency: BudgetCurrency,
) : TransactionData(id, comment, date, emptyList(), amount, currency)

sealed class CategoryData(
    open val currency: BudgetCurrency,
    open val id: String,
    open val name: String,
    open val iconName: String?
)

data class ExpenseCategory(
    override val currency: BudgetCurrency,
    override val id: String,
    override val name: String,
    override val iconName: String?,
) : CategoryData(currency, id, name, iconName)

data class IncomeCategory(
    override val currency: BudgetCurrency,
    override val id: String,
    override val name: String,
    override val iconName: String?,
) : CategoryData(currency, id, name, iconName)