package ru.bysoft.android.budget.uikit.components.buttons.entity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import ru.bysoft.android.budget.uikit.colors.UiKitColors

/** The only destructive style is text: a filled red button is not part of the design. */
enum class ButtonType {
    PRIMARY,
    SECONDARY,
    OUTLINE,
    TEXT,
    DESTRUCTIVE_TEXT,
}

/** Resolved colours of one button; [border] is null for the borderless variants. */
@Immutable
data class ButtonStyle(
    val container: Color,
    val content: Color,
    val border: Color? = null,
)

/** One mapping from the palette tokens covers both themes; pressed and disabled states are modifiers. */
@Composable
fun ButtonType.style(): ButtonStyle {
    val tokens = UiKitColors.colors.tokens
    return when (this) {
        ButtonType.PRIMARY -> ButtonStyle(container = tokens.accent, content = Color.White)
        ButtonType.SECONDARY -> ButtonStyle(container = tokens.surface2, content = tokens.textHigh)
        ButtonType.OUTLINE -> ButtonStyle(
            container = Color.Transparent,
            content = tokens.textHigh,
            border = tokens.line
        )
        ButtonType.TEXT -> ButtonStyle(container = Color.Transparent, content = tokens.accent)
        ButtonType.DESTRUCTIVE_TEXT -> ButtonStyle(container = Color.Transparent, content = tokens.negative)
    }
}
