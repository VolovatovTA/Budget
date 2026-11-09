package ru.bysoft.android.budget.uikit.components.buttons.entity

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.uikit.styles.BudgetShapes

/**
 * Button heights of the 2026 refresh: [LARGE] is the one primary action of a screen, [MEDIUM] is
 * every other button, [SMALL] fits inline next to text.
 */
enum class ButtonSize(
    val height: Dp,
    val shape: RoundedCornerShape,
    val iconSize: Dp,
    val horizontalPadding: Dp,
) {
    SMALL(height = 36.dp, shape = BudgetShapes.small, iconSize = 16.dp, horizontalPadding = 14.dp),
    MEDIUM(height = 48.dp, shape = BudgetShapes.control, iconSize = 20.dp, horizontalPadding = 20.dp),
    LARGE(height = 52.dp, shape = BudgetShapes.control, iconSize = 24.dp, horizontalPadding = 24.dp),
}
