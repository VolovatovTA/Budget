package ru.bysoft.budget.common.navigation.home

import androidx.compose.ui.graphics.Color
import ru.bysoft.budget.common.navigation.BottomNavigationButtonInfo
import ru.bysoft.budget.common.navigation.BottomNavigationInfo
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.icons.another.Wallet

object Wallet: BottomNavigationInfo("wallet", Wallet), BottomNavigationButtonInfo {
    override val label: String? = null
    override val backgroundColor: Color = UiKitColors.white
}