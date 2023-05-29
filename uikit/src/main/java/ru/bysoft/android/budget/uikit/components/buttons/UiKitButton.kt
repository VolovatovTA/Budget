package ru.bysoft.android.budget.uikit.components.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.buttons.entity.BudgetButtonColors
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonSize
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.corner
import ru.bysoft.android.budget.uikit.styles.halfPadding
import ru.bysoft.android.budget.uikit.styles.padding
import ru.bysoft.android.budget.uikit.theme.NoRippleTheme

@Composable
fun UiKitButton(
    info: UiKitButtonInfo,
    modifier: Modifier = Modifier,
    isButtonEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    val source = remember { MutableInteractionSource() }
    val isButtonPressed by source.collectIsPressedAsState()

    val colors = when (info.type) {
        ButtonType.PRIMARY -> BudgetButtonColors.primary(isButtonPressed)
        ButtonType.SECONDARY -> BudgetButtonColors.secondary(isButtonPressed)
        ButtonType.TERTIARY -> BudgetButtonColors.tertiary(isButtonPressed)
        ButtonType.OUTLINE -> BudgetButtonColors.outline(isButtonPressed)
        ButtonType.ERROR -> BudgetButtonColors.error(isButtonPressed)
        ButtonType.ERROR_SECONDARY -> BudgetButtonColors.errorSecondary(isButtonPressed)
    }


    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        if (info.type == ButtonType.OUTLINE) {
            val borderStroke = BudgetButtonColors.borderStroke(isButtonPressed, isButtonEnabled)
            OutlinedButton(
                onClick = onClick,
                modifier = modifier
                    .clip(RoundedCornerShape(info.size.radius))
                    .height(info.size.height),
                colors = colors,
                enabled = isButtonEnabled,
                interactionSource = source,
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 5.dp,
                    disabledElevation = 0.dp,
                    focusedElevation = 0.dp,
                    hoveredElevation = 0.dp
                ),
                border = borderStroke
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(padding)
                ) {
                    info.painterLeftImage?.let {
                        Image(
                            painter = info.painterLeftImage,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = halfPadding)
                                .size(info.size.iconSize)
                        )
                    }

                    Text(
                        info.text.uppercase(),
                        color = colors.contentColor(enabled = isButtonEnabled).value,
                        style = when (info.size) {
                            ButtonSize.SMALL -> UiKitTypography.TextSM.Medium
                            ButtonSize.MEDIUM -> UiKitTypography.TextMD.Medium
                            ButtonSize.BIG -> UiKitTypography.TextLG.Medium
                            ButtonSize.LARGE -> UiKitTypography.TextMD.Bold
                        }
                    )
                }
            }
        } else {
            Button(
                onClick = onClick,
                modifier = modifier
                    .clip(RoundedCornerShape(info.size.radius))
                    .height(info.size.height),
                colors = colors,
                enabled = isButtonEnabled,
                interactionSource = source,
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 5.dp,
                    disabledElevation = 0.dp,
                    focusedElevation = 0.dp,
                    hoveredElevation = 0.dp
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(padding)
                ) {
                    info.painterLeftImage?.let {
                        Image(
                            painter = info.painterLeftImage,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = halfPadding)
                                .size(info.size.iconSize)
                        )
                    }

                    Text(
                        info.text.uppercase(),
                        color = colors.contentColor(enabled = isButtonEnabled).value,
                        style = when (info.size) {
                            ButtonSize.SMALL -> UiKitTypography.TextSM.Medium
                            ButtonSize.MEDIUM -> UiKitTypography.TextMD.Medium
                            ButtonSize.BIG -> UiKitTypography.TextLG.Medium
                            ButtonSize.LARGE -> UiKitTypography.TextMD.Bold
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun UiKitSocialMediaButton(
    text: String,
    painterLeftImage: Painter,
    modifier: Modifier = Modifier,
    isButtonEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    val source = remember { MutableInteractionSource() }
    val isButtonPressed by source.collectIsPressedAsState()
    val colors = ButtonDefaults.buttonColors(
        backgroundColor = if (isButtonPressed) {
            UiKitColors.colors.neutral.`200`
        } else {
            UiKitColors.colors.neutral.`100`
        },
        contentColor = UiKitColors.colors.type.high
    )
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {

        Button(
            onClick = onClick,
            modifier = modifier
                .clip(RoundedCornerShape(corner))
                .height(44.dp),
            colors = colors,
            enabled = isButtonEnabled,
            interactionSource = source,
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 5.dp,
                disabledElevation = 0.dp,
                focusedElevation = 0.dp,
                hoveredElevation = 0.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(padding)
            ) {
                Image(
                    painter = painterLeftImage,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = halfPadding)
                )

                Text(
                    text,
                    color = UiKitColors.colors.type.high,
                    style = UiKitTypography.TextMD.Medium
                )
            }
        }
    }
}
