package ru.bysoft.android.budget.features.bottom_navigation.host.navigation.qr

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.BottomNavigationButtonInfo
import ru.bysoft.android.budget.common.navigation.NavigationInfo
import ru.bysoft.android.budget.uikit.icons.another.Qrcode

object QRCode : NavigationInfo("qrCode", "qrScreenName"), BottomNavigationButtonInfo {
    override val icon: ImageVector = Qrcode
    override val label: Int? = null
    @Composable
    override fun backgroundColor() = ru.bysoft.android.budget.uikit.colors.UiKitColors.colors.light

}