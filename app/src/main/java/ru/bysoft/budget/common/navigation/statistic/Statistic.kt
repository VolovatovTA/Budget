package ru.bysoft.budget.common.navigation.statistic

import androidx.compose.ui.graphics.Color
import ru.bysoft.budget.common.navigation.BottomNavigationButtonInfo
import ru.bysoft.budget.common.navigation.BottomNavigationInfo
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.icons.another.Qrcode
import ru.bysoft.budget.uikit.icons.pack.Plus
import ru.bysoft.budget.uikit.icons.pack.Statistic

object Statistic : BottomNavigationInfo("statistic", Statistic), BottomNavigationButtonInfo {
    override val label: String? = null
    override val backgroundColor: Color = UiKitColors.white

}

object QRCode : BottomNavigationInfo("qrCode", Qrcode), BottomNavigationButtonInfo {
    override val label: String? = null
    override val backgroundColor: Color = UiKitColors.white

}

object Plus : BottomNavigationInfo("plus", Plus), BottomNavigationButtonInfo {
    override val label: String? = null
    override val backgroundColor: Color = UiKitColors.col4

}