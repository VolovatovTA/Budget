package ru.bysoft.budget.features.bottom_navigation.statistic.presentation.entity

import androidx.compose.ui.graphics.vector.ImageVector

sealed interface IStatisticState

data class StatisticSuccessState(
    val listInfo: List<CategoryInfo>
): IStatisticState

object StatisticErrorState: IStatisticState

data class StatisticWaitingState(
    val isRefreshing: Boolean
): IStatisticState

data class CategoryInfo(
    val icon: ImageVector?,
    val name: String,
    val subtitle: String,
    val amount: String,
    val id: String
)