package ru.bysoft.android.budget.uikit.components.buttons

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonSize
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonStyle
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.components.buttons.entity.style
import ru.bysoft.android.budget.uikit.styles.BudgetSpacing
import ru.bysoft.android.budget.uikit.styles.UiKitTypography

/** Text is rendered as given: sentence case comes from the string resource, never from the component. */
@Composable
fun UiKitButton(
    info: UiKitButtonInfo,
    modifier: Modifier = Modifier,
    isButtonEnabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    val style = info.type.style()
    ButtonSurface(size = info.size, style = style, enabled = isButtonEnabled, onClick = onClick, modifier = modifier) {
        info.painterLeftImage?.let { painter ->
            Image(painter = painter, contentDescription = null, modifier = Modifier.size(info.size.iconSize))
        }
        Text(text = info.text, color = style.content, style = info.size.textStyle(), maxLines = 1)
    }
}

/** Sign-in-with-provider button: secondary surface with the provider logo on the left. */
@Composable
fun UiKitSocialMediaButton(
    text: String,
    painterLeftImage: Painter,
    modifier: Modifier = Modifier,
    isButtonEnabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    val size = ButtonSize.MEDIUM
    val style = ButtonType.SECONDARY.style()
    ButtonSurface(size = size, style = style, enabled = isButtonEnabled, onClick = onClick, modifier = modifier) {
        Image(painter = painterLeftImage, contentDescription = null, modifier = Modifier.size(size.iconSize))
        Text(text = text, color = style.content, style = size.textStyle(), maxLines = 1)
    }
}

/**
 * Shared chrome of every kit button: shape and colours from [style], ripple inside the clip,
 * 0.97 scale while pressed, 40 % opacity when disabled. Content is centred, so a `fillMaxWidth`
 * caller gets a centred label and a wrap-content caller gets a compact pill.
 */
@Composable
private fun ButtonSurface(
    size: ButtonSize,
    style: ButtonStyle,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val source = remember { MutableInteractionSource() }
    val pressed by source.collectIsPressedAsState()
    val scale by animateFloatAsState(targetValue = if (pressed) PRESSED_SCALE else 1f, label = "buttonScale")
    val borderModifier = style.border?.let { Modifier.border(BORDER_WIDTH, it, size.shape) } ?: Modifier
    Row(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .alpha(if (enabled) 1f else DISABLED_ALPHA)
            .height(size.height)
            .clip(size.shape)
            .background(style.container)
            .then(borderModifier)
            .clickable(
                interactionSource = source,
                indication = ripple(),
                enabled = enabled,
                role = Role.Button,
                onClick = onClick,
            )
            .padding(horizontal = size.horizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BudgetSpacing.s, Alignment.CenterHorizontally),
        content = content,
    )
}

@Composable
private fun ButtonSize.textStyle(): TextStyle = when (this) {
    ButtonSize.SMALL -> UiKitTypography.TextSM.SemiBold
    ButtonSize.MEDIUM, ButtonSize.LARGE -> UiKitTypography.TextMD.SemiBold
}

private const val PRESSED_SCALE = 0.97f
private const val DISABLED_ALPHA = 0.4f
private val BORDER_WIDTH = 1.5.dp
