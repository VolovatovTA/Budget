package ru.bysoft.budget.common.navigation.qr

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import ru.bysoft.budget.common.navigation.BottomNavigationButtonInfo
import ru.bysoft.budget.common.navigation.NavigationInfo
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.icons.another.Qrcode

object QRCode : NavigationInfo("qrCode"), BottomNavigationButtonInfo {
    override val icon: ImageVector = Qrcode
    override val label: String? = null
    override val backgroundColor: Color = UiKitColors.white

}