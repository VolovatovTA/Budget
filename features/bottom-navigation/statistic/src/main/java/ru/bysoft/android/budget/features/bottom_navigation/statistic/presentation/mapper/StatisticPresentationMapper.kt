package ru.bysoft.android.budget.features.bottom_navigation.statistic.presentation.mapper

import ru.bysoft.android.budget.common.data_entity.ExpenseCategory
import ru.bysoft.android.budget.common.util.getBeautifulAmount
import ru.bysoft.android.budget.common.data_entity.StatisticData
import ru.bysoft.android.budget.features.bottom_navigation.statistic.presentation.entity.CategoryInfo
import ru.bysoft.android.budget.features.bottom_navigation.statistic.presentation.entity.ProgressInfo
import ru.bysoft.android.budget.features.bottom_navigation.statistic.presentation.entity.ProgressInfoWaiting
import ru.bysoft.android.budget.features.bottom_navigation.statistic.presentation.entity.StatisticSuccessState
import ru.bysoft.android.budget.uikit.components.listItem.entity.UiKitAmountInfo
import ru.bysoft.android.budget.uikit.components.listItem.entity.UiKitAmountInfoWaiting
import ru.bysoft.android.budget.uikit.icons.UiKitIcons
import javax.inject.Inject

class StatisticPresentationMapper @Inject constructor() {
    fun getState(
        data: StatisticData,
        amountInfo: UiKitAmountInfo = UiKitAmountInfoWaiting
    ): StatisticSuccessState =
        StatisticSuccessState(
            listInfo = data.listCategoryData.map { category ->
                getCategoryState(
                    category,
                    amountInfo,
                    ProgressInfoWaiting.takeIf { category.limitAmount != null || category.limitType != null })
            }
        )

    fun getCategoryState(
        categoryData: ExpenseCategory,
        amountInfo: UiKitAmountInfo,
        progress: ProgressInfo?
    ): CategoryInfo =
        CategoryInfo(
            amount = amountInfo,
            icon = UiKitIcons.getByName(categoryData.iconName),
            subtitle = categoryData.limitAmount?.let {
                "${getBeautifulAmount(it, categoryData.currency)} "
            },
            subtitleAddition = categoryData.limitType?.textToShow,
            name = categoryData.name,
            id = categoryData.id,
            progressInfo = progress
        )
}