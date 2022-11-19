package ru.bysoft.budget.common.navigation.home

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import ru.bysoft.budget.common.navigation.BottomNavigationButtonInfo
import ru.bysoft.budget.common.navigation.NavigationInfo
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.icons.another.Wallet

object Wallet : NavigationInfo("wallet", "walletScreenName"), BottomNavigationButtonInfo {
    override val icon: ImageVector = Wallet
    override val label: String? = null
    override val backgroundColor: Color = UiKitColors.white
}