package ru.bysoft.android.budget.features.bottom_navigation.statistic.data.entity

import ru.bysoft.android.budget.common.util.BudgetCurrency
import ru.bysoft.android.budget.common.util.PeriodState


data class StatisticData(
    val listCategoryData: List<CategoryData>
)

data class CategoryData(
    val currency: BudgetCurrency,
    val iconName: String?,
    val id: String,
    val name: String,
    val limitType: PeriodState?,
    val limitAmount: Float?,
    val amount: Float? = null,
)