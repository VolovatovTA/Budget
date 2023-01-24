package com.example.bottom_navigation.navigation.statistic

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bottom_navigation.navigation.BottomNavigationButtonInfo
import ru.bysoft.budget.common.navigation.NavigationInfo
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.icons.pack.Statistic

object Statistic : NavigationInfo("statistic", "statisticScreenName"), BottomNavigationButtonInfo {
    override val icon: ImageVector = Statistic
    override val label: String? = null
    @Composable
    override fun backgroundColor() = UiKitColors.colors.light

}