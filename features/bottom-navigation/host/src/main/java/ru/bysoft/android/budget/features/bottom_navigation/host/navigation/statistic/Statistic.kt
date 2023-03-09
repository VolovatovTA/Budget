package ru.bysoft.android.budget.features.bottom_navigation.host.navigation.statistic

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.BottomNavigationButtonInfo
import ru.bysoft.android.budget.common.navigation.NavigationInfo

object Statistic : NavigationInfo(route = "statistic", screenName = "statisticScreenName"),
    BottomNavigationButtonInfo {
    override val icon: ImageVector =  ru.bysoft.android.budget.uikit.icons.pack.Statistic
    override val label: Int? = null

    @Composable
    override fun backgroundColor() = ru.bysoft.android.budget.uikit.colors.UiKitColors.colors.light

}