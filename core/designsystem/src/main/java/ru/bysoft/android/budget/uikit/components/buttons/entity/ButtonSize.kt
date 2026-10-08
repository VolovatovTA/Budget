package ru.bysoft.android.budget.uikit.components.buttons.entity

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.uikit.colors.UiKitColors

enum class ButtonSize(val height: Dp, val radius: Dp, val iconSize: Dp) {
    SMALL(36.dp, 6.dp, 16.dp),
    MEDIUM(44.dp, 8.dp, 20.dp),
    BIG(52.dp, 8.dp, 24.dp),
    LARGE(60.dp, 8.dp, 24.dp);
}

object BudgetButtonColors {
    @Composable
    fun primary(isButtonPressed: Boolean): ButtonColors =
        if (isSystemInDarkTheme())
            ButtonDefaults.buttonColors(
                backgroundColor =
                if (isButtonPressed) UiKitColors.colors.primary.`600`
                else UiKitColors.colors.primary.`700`,
                contentColor = Color.White,
                disabledBackgroundColor = UiKitColors.colors.primary.`600`.copy(alpha = 0.25f),
                disabledContentColor = Color.White
            )
        else
            ButtonDefaults.buttonColors(
                backgroundColor =
                if (isButtonPressed) UiKitColors.colors.primary.`600`
                else UiKitColors.colors.primary.`700`,
                contentColor = Color.White,
                disabledBackgroundColor = UiKitColors.colors.primary.`200`,
                disabledContentColor = Color.White
            )

    @Composable
    fun secondary(isButtonPressed: Boolean): ButtonColors =
        if (isSystemInDarkTheme())
            ButtonDefaults.buttonColors(
                backgroundColor =
                if (isButtonPressed) UiKitColors.colors.primary.`1000`
                else UiKitColors.colors.primary.`900`,
                contentColor = UiKitColors.colors.primary.`100`,
                disabledBackgroundColor = UiKitColors.colors.primary.`300`,
                disabledContentColor = UiKitColors.colors.primary.`200`
            )
        else
            ButtonDefaults.buttonColors(
                backgroundColor =
                if (isButtonPressed) UiKitColors.colors.primary.`100`
                else UiKitColors.colors.primary.`200`,
                contentColor = UiKitColors.colors.primary.`700`,
                disabledBackgroundColor = UiKitColors.colors.primary.`300`,
                disabledContentColor = UiKitColors.colors.primary.`200`
            )

    @Composable
    fun outline(isButtonPressed: Boolean): ButtonColors =
        if (isSystemInDarkTheme())
            ButtonDefaults.buttonColors(
                backgroundColor =
                if (isButtonPressed) Color.Transparent
                else Color.Transparent,
                contentColor = UiKitColors.colors.type.high,
                disabledBackgroundColor = Color.Transparent,
                disabledContentColor = UiKitColors.colors.type.low
            )
        else
            ButtonDefaults.buttonColors(
                backgroundColor =
                if (isButtonPressed) Color.Transparent
                else Color.Transparent,
                contentColor = UiKitColors.colors.type.high,
                disabledBackgroundColor = Color.Transparent,
                disabledContentColor = UiKitColors.colors.type.low
            )

    @Composable
    fun borderStroke(isButtonPressed: Boolean, isButtonEnabled: Boolean): BorderStroke =
        if (isSystemInDarkTheme())
            BorderStroke(
                1.5.dp,
                when{
                    !isButtonEnabled -> UiKitColors.colors.neutral.`400`
                    isButtonPressed -> UiKitColors.colors.neutral.`500`
                    else -> UiKitColors.colors.neutral.`400`
                }
            )
        else
            BorderStroke(
                1.5.dp,
                when{
                    !isButtonEnabled -> UiKitColors.colors.neutral.`400`
                    isButtonPressed -> UiKitColors.colors.neutral.`500`
                    else -> UiKitColors.colors.neutral.`400`
                }
            )

    @Composable
    fun tertiary(isButtonPressed: Boolean): ButtonColors =
        if (isSystemInDarkTheme())
            ButtonDefaults.buttonColors(
                backgroundColor =
                if (isButtonPressed) Color.White
                else Color.Transparent,
                contentColor = UiKitColors.colors.primary.`600`,
                disabledBackgroundColor = Color.Transparent,
                disabledContentColor = UiKitColors.colors.primary.`200`
            )
        else
            ButtonDefaults.buttonColors(
                backgroundColor =
                if (isButtonPressed) Color.White
                else Color.Transparent,
                contentColor = UiKitColors.colors.primary.`600`,
                disabledBackgroundColor = Color.Transparent,
                disabledContentColor = UiKitColors.colors.primary.`1000`
            )

    @Composable
    fun error(isButtonPressed: Boolean): ButtonColors =
        if (isSystemInDarkTheme())
            ButtonDefaults.buttonColors(
                backgroundColor =
                if (isButtonPressed) UiKitColors.colors.feedbackRed.`700`
                else UiKitColors.colors.feedbackRed.`600`,
                contentColor = Color.White,
                disabledBackgroundColor = UiKitColors.colors.feedbackRed.`100`,
                disabledContentColor = Color.White
            )
        else
            ButtonDefaults.buttonColors(
                backgroundColor =
                if (isButtonPressed) UiKitColors.colors.feedbackRed.`700`
                else UiKitColors.colors.feedbackRed.`600`,
                contentColor = Color.White,
                disabledBackgroundColor = UiKitColors.colors.feedbackRed.`600`.copy(alpha = 0.5f),
                disabledContentColor = Color.White
            )

    @Composable
    fun errorSecondary(isButtonPressed: Boolean): ButtonColors =
        if (isSystemInDarkTheme())
            ButtonDefaults.buttonColors(
                backgroundColor =
                if (isButtonPressed) UiKitColors.colors.feedbackRed.`700`
                else UiKitColors.colors.feedbackRed.`200`,
                contentColor = UiKitColors.colors.feedbackRed.`700`,
                disabledBackgroundColor = UiKitColors.colors.feedbackRed.`400`,
                disabledContentColor = UiKitColors.colors.feedbackRed.`200`
            )
        else
            ButtonDefaults.buttonColors(
                backgroundColor =
                if (isButtonPressed) UiKitColors.colors.feedbackRed.`700`
                else UiKitColors.colors.feedbackRed.`200`,
                contentColor = UiKitColors.colors.feedbackRed.`700`,
                disabledBackgroundColor = UiKitColors.colors.feedbackRed.`100`,
                disabledContentColor = UiKitColors.colors.feedbackRed.`200`
            )
}

enum class ButtonType {
    PRIMARY,
    SECONDARY,
    OUTLINE,
    TERTIARY,
    ERROR,
    ERROR_SECONDARY
}