package ru.bysoft.budget.features.bottom_navigation.statistic.presentation.entity

import androidx.compose.ui.graphics.vector.ImageVector
import ru.bysoft.budget.common.util.PeriodState
import ru.bysoft.budget.uikit.components.currecyfield.entity.PopupFieldState
import ru.bysoft.budget.uikit.components.listItem.entity.UiKitAmountInfo

sealed class IStatisticState(
    val periodState: PopupFieldState<PeriodState> = PopupFieldState(
        selectedValue = PeriodState.NO_PERIOD,
        list = listOf(
            PeriodState.DAY,
            PeriodState.WEEK,
            PeriodState.MONTH,
            PeriodState.NO_PERIOD,
        ),
    ),
)

data class StatisticSuccessState(
    val listInfo: List<CategoryInfo>
): IStatisticState()

object StatisticErrorState: IStatisticState()

data class StatisticWaitingState(
    val isRefreshing: Boolean
): IStatisticState()

data class CategoryInfo(
    val icon: ImageVector?,
    val name: String,
    val subtitle: String?,
    val subtitleAddition: Int?,
    val amount: UiKitAmountInfo,
    val progressInfo: ProgressInfo,
    val id: String
)

sealed interface ProgressInfo

object ProgressInfoWaiting: ProgressInfo

data class ProgressInfoSuccess(
    val progress: Float
): ProgressInfo

object ProgressInfoError: ProgressInfo