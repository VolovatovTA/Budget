package ru.bysoft.android.budget.features.bottom_navigation.host.navigation.home

import androidx.compose.runtime.Composable
import ru.bysoft.android.budget.common.navigation.NavigationInfo
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.BottomNavigationButtonInfo
import ru.bysoft.android.features.bottom_navigation.host.R

object Home : NavigationInfo("wallet", "walletScreenName"), BottomNavigationButtonInfo {
    override val iconId: Int = ru.bysoft.android.budget.uikit.R.drawable.home_05
    override val label: Int = R.string.label_home
    @Composable
    override fun backgroundColor() = ru.bysoft.android.budget.uikit.colors.UiKitColors.colors.primary.`100`
}