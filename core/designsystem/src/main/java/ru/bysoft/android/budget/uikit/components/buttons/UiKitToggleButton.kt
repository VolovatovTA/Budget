package ru.bysoft.android.budget.uikit.components.buttons

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonSize
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.styles.UiKitTypography

@Composable
fun UiKitToggleButton(
    info: UiKitButtonInfo,
    modifier: Modifier = Modifier,
    checked: Boolean = true,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    val backgroundColor = when {
        !enabled -> UiKitColors.colors.neutral.`200`
        checked -> UiKitColors.colors.neutral.`800`
        else -> UiKitColors.colors.neutral.`600`
    }

    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .height(32.dp),
        color = backgroundColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null,
                    onClick = { onCheckedChange(!checked) }
                )
                .padding(horizontal = 25.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                info.text.uppercase(),
                fontSize = 10.sp,
                style = UiKitTypography.TextSM.Bold,
                color = when {
                    !enabled -> Color.White
                    checked -> Color.White
                    else -> Color.White
                },
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
fun ButtonsLight() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        UiKitToggleButton(
            info = UiKitButtonInfo(
                text = "Не нажатая",
                size = ButtonSize.MEDIUM
            ),
            checked = false
        )
        UiKitToggleButton(
            info = UiKitButtonInfo(
                text = "Нажатая",
                size = ButtonSize.MEDIUM
            ),
            checked = true
        )
        UiKitToggleButton(
            info = UiKitButtonInfo(
                text = "Выключенна",
                size = ButtonSize.MEDIUM
            ),
            checked = true,
            enabled = false
        )
    }


}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ButtonsDark() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        UiKitToggleButton(
            info = UiKitButtonInfo(
                text = "Не нажатая",
                size = ButtonSize.MEDIUM
            ),
            checked = false
        )
        UiKitToggleButton(
            info = UiKitButtonInfo(
                text = "Нажатая",
                size = ButtonSize.MEDIUM
            ),
            checked = true
        )
        UiKitToggleButton(
            info = UiKitButtonInfo(
                text = "Выключеная",
                size = ButtonSize.MEDIUM
            ),
            checked = true,
            enabled = false
        )
    }


}