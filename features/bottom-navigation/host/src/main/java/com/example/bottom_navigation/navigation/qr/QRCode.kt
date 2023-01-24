package com.example.bottom_navigation.navigation.qr

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bottom_navigation.navigation.BottomNavigationButtonInfo
import ru.bysoft.budget.common.navigation.NavigationInfo
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.icons.another.Qrcode

object QRCode : NavigationInfo("qrCode", "qrScreenName"), BottomNavigationButtonInfo {
    override val icon: ImageVector = Qrcode
    override val label: String? = null
    @Composable
    override fun backgroundColor() = UiKitColors.colors.light

}