package ru.bysoft.android.budget.features.bottom_navigation.host.navigation.qr

import androidx.compose.runtime.Composable
import ru.bysoft.android.budget.common.navigation.NavigationInfo
import ru.bysoft.android.budget.features.bottom_navigation.host.navigation.BottomNavigationButtonInfo
import ru.bysoft.android.features.bottom_navigation.host.R

object QRCode : NavigationInfo("qrCode", "qrScreenName"), BottomNavigationButtonInfo {
    override val iconId: Int = ru.bysoft.android.budget.uikit.R.drawable.scan
    override val label: Int = R.string.label_qr
    @Composable
    override fun backgroundColor() = ru.bysoft.android.budget.uikit.colors.UiKitColors.colors.primary.`100`

}