package ru.bysoft.android.budget.features.bottom_navigation.host.navigation.statistic

import androidx.compose.runtime.Composable
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.BottomNavigationButtonInfo
import ru.bysoft.android.budget.common.navigation.NavigationInfo
import ru.bysoft.android.features.bottom_navigation.host.R

object Statistic : NavigationInfo(route = "statistic", screenName = "statisticScreenName"),
    BottomNavigationButtonInfo {
    override val iconId: Int = ru.bysoft.android.budget.uikit.R.drawable.horizontal_chart_03
    override val label: Int = R.string.label_statistic

    @Composable
    override fun backgroundColor() = ru.bysoft.android.budget.uikit.colors.UiKitColors.colors.primary.`100`

}