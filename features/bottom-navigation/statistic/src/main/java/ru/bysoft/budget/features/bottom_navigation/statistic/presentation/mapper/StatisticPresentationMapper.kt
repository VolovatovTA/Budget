package ru.bysoft.budget.features.bottom_navigation.statistic.presentation.mapper

import ru.bysoft.budget.common.util.getBeautifulAmount
import ru.bysoft.budget.features.bottom_navigation.statistic.data.entity.CategoryData
import ru.bysoft.budget.features.bottom_navigation.statistic.data.entity.StatisticData
import ru.bysoft.budget.features.bottom_navigation.statistic.presentation.entity.CategoryInfo
import ru.bysoft.budget.features.bottom_navigation.statistic.presentation.entity.ProgressInfo
import ru.bysoft.budget.features.bottom_navigation.statistic.presentation.entity.ProgressInfoWaiting
import ru.bysoft.budget.features.bottom_navigation.statistic.presentation.entity.StatisticSuccessState
import ru.bysoft.budget.uikit.components.listItem.entity.UiKitAmountInfo
import ru.bysoft.budget.uikit.components.listItem.entity.UiKitAmountInfoWaiting
import ru.bysoft.budget.uikit.icons.UiKitIcons
import javax.inject.Inject

class StatisticPresentationMapper @Inject constructor() {
    fun getState(data: StatisticData, amountInfo: UiKitAmountInfo = UiKitAmountInfoWaiting): StatisticSuccessState =
        StatisticSuccessState(
            listInfo = data.listCategoryData.map { getCategoryState(it, amountInfo, ProgressInfoWaiting) }
        )

    fun getCategoryState(
        categoryData: CategoryData,
        amountInfo: UiKitAmountInfo,
        progress: ProgressInfo
    ): CategoryInfo =
        CategoryInfo(
            amount = amountInfo,
            icon = UiKitIcons.getByName(categoryData.iconName),
            subtitle = categoryData.limitAmount?.let {
                "Лимит ${
                    getBeautifulAmount(
                        it,
                        categoryData.currency
                    )
                } ${categoryData.limitType?.textToShow}"
            },
            name = categoryData.name,
            id = categoryData.id,
            progressInfo = progress
        )
}