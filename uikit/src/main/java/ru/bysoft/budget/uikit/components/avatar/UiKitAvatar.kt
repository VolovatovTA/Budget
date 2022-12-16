package ru.bysoft.budget.uikit.components.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.uikit.colors.UiKitColors

@Composable
fun UiKitAvatar(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    backgroundColor: Color = UiKitColors.colors.col3,
    tintColor: Color = UiKitColors.colors.dark,
    elevation: Dp = 3.dp,
    onClick: () -> Unit = {}
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        elevation = elevation
    ) {
        Box(
            modifier = modifier
                .size(40.dp)
                .background(backgroundColor)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = tintColor
            )

        }
    }

}