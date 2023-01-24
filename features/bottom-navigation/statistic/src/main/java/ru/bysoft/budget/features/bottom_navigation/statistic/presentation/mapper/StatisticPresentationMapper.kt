package ru.bysoft.budget.features.bottom_navigation.statistic.presentation.mapper

import ru.bysoft.budget.common.util.getBeautifulAmount
import ru.bysoft.budget.common.util.getCurrency
import ru.bysoft.budget.features.bottom_navigation.statistic.data.entity.CategoryData
import ru.bysoft.budget.features.bottom_navigation.statistic.data.entity.StatisticData
import ru.bysoft.budget.features.bottom_navigation.statistic.presentation.entity.CategoryInfo
import ru.bysoft.budget.features.bottom_navigation.statistic.presentation.entity.StatisticSuccessState
import ru.bysoft.budget.uikit.icons.UiKitIcons
import javax.inject.Inject

class StatisticPresentationMapper @Inject constructor() {
    fun getState(data: StatisticData): StatisticSuccessState =
        StatisticSuccessState(
            listInfo = data.listCategoryData.map { getCategoryState(it) }
        )

    private fun getCategoryState(categoryData: CategoryData): CategoryInfo =
        CategoryInfo(
            amount = getBeautifulAmount(500f, categoryData.currency),
            icon = UiKitIcons.getByName(categoryData.iconName),
            subtitle = "Лимит ${getBeautifulAmount(1500f, getCurrency("EUR")!!)}",
            name = categoryData.name,
            id = categoryData.id
        )
}