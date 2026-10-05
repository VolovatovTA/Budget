package ru.bysoft.android.budget.features.bottom_navigation.statistic.presentation.entity

import androidx.annotation.DrawableRes
import ru.bysoft.android.budget.uikit.components.listItem.entity.UiKitAmountInfo

sealed interface IStatisticState

data class StatisticSuccessState(
    val listInfo: List<CategoryInfo>
): IStatisticState

object StatisticErrorState: IStatisticState

data class StatisticWaitingState(
    val isRefreshing: Boolean
): IStatisticState

data class CategoryInfo(
    @DrawableRes val icon: Int?,
    val name: String,
    val subtitle: String?,
    val subtitleAddition: Int?,
    val amount: UiKitAmountInfo,
    val progressInfo: ProgressInfo?,
    val id: String
)

sealed interface ProgressInfo

object ProgressInfoWaiting: ProgressInfo

data class ProgressInfoSuccess(
    val progress: Float
): ProgressInfo

object ProgressInfoError: ProgressInfo