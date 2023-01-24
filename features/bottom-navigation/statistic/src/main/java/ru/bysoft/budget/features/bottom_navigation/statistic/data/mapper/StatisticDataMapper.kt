package ru.bysoft.budget.features.bottom_navigation.statistic.data.mapper

import ru.bysoft.budget.common.util.getCurrency
import ru.bysoft.budget.features.bottom_navigation.statistic.data.entity.CategoryData
import ru.bysoft.budget.features.bottom_navigation.statistic.data.entity.StatisticData
import ru.bysoft.budget.features.bottom_navigation.statistic.data.network.entity.CategoryExpenseResponse
import ru.bysoft.budget.features.bottom_navigation.statistic.data.network.entity.StatisticExpenseResponse
import javax.inject.Inject

class StatisticDataMapper @Inject constructor() {
    fun mapToData(response: StatisticExpenseResponse): StatisticData {
        return StatisticData(
            listCategoryData = response.data.map { getCategoryData(it) }
        )
    }

    private fun getCategoryData(categoryExpense: CategoryExpenseResponse): CategoryData =
        CategoryData(
            currency = getCurrency(categoryExpense.currency)!!,
            name = categoryExpense.name,
            iconName = categoryExpense.iconName,
            id = categoryExpense.id
        )
}