package ru.bysoft.budget.uikit.components.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.buttons.entity.BadgeButtonColors
import ru.bysoft.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.budget.uikit.theme.Ermilov

@Composable
fun UiKitButton(
    info: UiKitButtonInfo,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .height(32.dp)
        ,
        colors = BadgeButtonColors(
            backgroundColor = UiKitColors.colors.col6,
            contentColor = UiKitColors.colors.white,
            disabledBackgroundColor = UiKitColors.colors.col3,
            disabledContentColor = UiKitColors.colors.white
        ),
        enabled = enabled
    ) {
        Text(
            info.text.uppercase(),
            fontSize = 10.sp,
            fontFamily = Ermilov
        )
    }
}
