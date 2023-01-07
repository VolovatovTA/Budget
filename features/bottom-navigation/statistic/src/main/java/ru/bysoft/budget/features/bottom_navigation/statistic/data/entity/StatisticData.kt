package ru.bysoft.budget.features.bottom_navigation.statistic.data.entity

import ru.bysoft.budget.common.util.BudgetCurrency


data class StatisticData(
    val listCategoryData: List<CategoryData>
)

data class CategoryData(
//    val currency: BudgetCurrency,
    val iconName: String?,
    val id: String,
    val name: String
)