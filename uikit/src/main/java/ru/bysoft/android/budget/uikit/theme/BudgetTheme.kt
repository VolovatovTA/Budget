package ru.bysoft.android.budget.uikit.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import ru.bysoft.android.budget.uikit.R
import ru.bysoft.android.budget.uikit.colors.UiKitColors

internal val Roboto = FontFamily(Font(R.font.roboto))

internal val PublicSans = FontFamily(Font(R.font.public_sans))


object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}

@Composable
fun BudgetTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors(
            primary = UiKitColors.colors.surface.primary,
            surface = UiKitColors.colors.surface.primary,
            background = UiKitColors.colors.surface.primary,
        ),
        content = content,
    )
}