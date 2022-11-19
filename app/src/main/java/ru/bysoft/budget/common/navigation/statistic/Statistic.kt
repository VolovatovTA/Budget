package ru.bysoft.budget.common.navigation.statistic

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import ru.bysoft.budget.common.navigation.BottomNavigationButtonInfo
import ru.bysoft.budget.common.navigation.NavigationInfo
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.icons.pack.Plus
import ru.bysoft.budget.uikit.icons.pack.Statistic

object Statistic : NavigationInfo("statistic", "statisticScreenName"), BottomNavigationButtonInfo {
    override val icon: ImageVector = Statistic
    override val label: String? = null
    override val backgroundColor: Color = UiKitColors.white

}

object Plus : NavigationInfo("plus", "plusScreenName"), BottomNavigationButtonInfo {
    override val icon: ImageVector = Plus
    override val label: String? = null
    override val backgroundColor: Color = UiKitColors.col4
}