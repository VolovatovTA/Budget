package com.example.bottom_navigation.navigation.plus

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bottom_navigation.navigation.BottomNavigationButtonInfo
import com.example.bottom_navigation.navigation.NavigationInfo
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.icons.pack.Plus


object Plus : NavigationInfo("plus", "plusScreenName"), BottomNavigationButtonInfo {
    override val icon: ImageVector = Plus
    override val label: String? = null
    @Composable
    override fun backgroundColor() = UiKitColors.colors.col4
}