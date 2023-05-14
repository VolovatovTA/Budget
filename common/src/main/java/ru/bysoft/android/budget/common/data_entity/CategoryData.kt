package ru.bysoft.android.budget.common.data_entity

import ru.bysoft.android.budget.common.util.BudgetCurrency
import ru.bysoft.android.budget.common.util.PeriodState

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
    val limitType: PeriodState? = null,
    val limitAmount: Float? = null,
    val amount: Float? = null,
) : CategoryData(currency, id, name, iconName)

data class IncomeCategory(
    override val currency: BudgetCurrency,
    override val id: String,
    override val name: String,
    override val iconName: String?,
) : CategoryData(currency, id, name, iconName)


class CategoryErrorType(val messageFromBack: String?) : Throwable(messageFromBack) {
    companion object {
        val INVALID_CURRENCY = CategoryErrorType("invalid-currency")
        val INVALID_NAME = CategoryErrorType("invalid-name")
        val NO_UNIQ_NAME = CategoryErrorType("expense-name-musq-be-unique")
        val INVALID_ICON_NAME = CategoryErrorType("invalid-icon-name")
        val UNKNOWN_ERROR = CategoryErrorType(null)
        val NULL_ERROR = CategoryErrorType(null)
        val TECHNICAL_ERROR_IN_BACK = CategoryErrorType(null)
    }
}

data class StatisticData(
    val listCategoryData: List<ExpenseCategory>
)
