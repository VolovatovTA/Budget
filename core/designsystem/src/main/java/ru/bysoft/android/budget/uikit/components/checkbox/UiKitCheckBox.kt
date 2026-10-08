package ru.bysoft.android.budget.uikit.components.checkbox

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.styles.halfPadding

@Composable
fun UiKitCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val source = remember { MutableInteractionSource() }
    val isCheckBoxPressed by source.collectIsPressedAsState()
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = CheckboxDefaults.colors(
            checkedColor = UiKitColors.colors.primary.`600`,
            uncheckedColor = if (isSystemInDarkTheme()) UiKitColors.colors.neutral.`300` else UiKitColors.colors.neutral.`500`,
            checkmarkColor = if (!enabled) UiKitColors.colors.neutral.`400` else Color.White,
            disabledColor = UiKitColors.colors.neutral.`300`,
            disabledIndeterminateColor = UiKitColors.colors.neutral.`400`,
        ),
        modifier = modifier.shadow(
            elevation = if (isCheckBoxPressed) 2.dp else 0.dp
        ),
        interactionSource = source,
        enabled = enabled,
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun Light() {
    var isChecked1 by remember { mutableStateOf(false) }
    var isChecked2 by remember { mutableStateOf(true) }
    var isChecked3 by remember { mutableStateOf(true) }
    var isChecked4 by remember { mutableStateOf(false) }

    Row(horizontalArrangement = Arrangement.spacedBy(halfPadding)) {
        UiKitCheckBox(checked = isChecked1, onCheckedChange = { isChecked1 = !isChecked1 })
        UiKitCheckBox(checked = isChecked2, onCheckedChange = { isChecked2 = !isChecked2 })
        UiKitCheckBox(
            checked = isChecked3,
            onCheckedChange = { isChecked3 = !isChecked3 },
            enabled = false
        )
        UiKitCheckBox(
            checked = isChecked4,
            onCheckedChange = { isChecked4 = !isChecked4 },
            enabled = false
        )
    }

}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF0E1216
)
@Composable
fun Dark() {
    var isChecked1 by remember { mutableStateOf(false) }
    var isChecked2 by remember { mutableStateOf(true) }
    var isChecked3 by remember { mutableStateOf(true) }
    var isChecked4 by remember { mutableStateOf(false) }

    Row(horizontalArrangement = Arrangement.spacedBy(halfPadding)) {
        UiKitCheckBox(checked = isChecked1, onCheckedChange = { isChecked1 = !isChecked1 })
        UiKitCheckBox(checked = isChecked2, onCheckedChange = { isChecked2 = !isChecked2 })
        UiKitCheckBox(
            checked = isChecked3,
            onCheckedChange = { isChecked3 = !isChecked3 },
            enabled = false
        )
        UiKitCheckBox(
            checked = isChecked4,
            onCheckedChange = { isChecked4 = !isChecked4 },
            enabled = false
        )
    }

}

