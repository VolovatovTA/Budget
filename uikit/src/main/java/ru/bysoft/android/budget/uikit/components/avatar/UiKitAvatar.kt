package ru.bysoft.android.budget.uikit.components.avatar

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun UiKitAvatar(
    icon: ImageVector?,
    modifier: Modifier = Modifier,
    backgroundColor: Color = ru.bysoft.android.budget.uikit.colors.UiKitColors.colors.primary.`100`,
    tintColor: Color = ru.bysoft.android.budget.uikit.colors.UiKitColors.colors.primary.`1100`,
    elevation: Dp = 3.dp,
    onClick: () -> Unit = {},
    rippleEnabled: Boolean = true
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        elevation = elevation
    ) {
        Box(
            modifier = modifier
                .size(40.dp)
                .background(backgroundColor)
                .clickable(
                    remember { MutableInteractionSource() },
                    indication = if (rippleEnabled) LocalIndication.current else null
                ) { onClick() },
            contentAlignment = Alignment.Center
        ) {
            icon?.let {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = tintColor
                )
            }
        }
    }
}