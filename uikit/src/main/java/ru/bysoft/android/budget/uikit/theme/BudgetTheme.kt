package ru.bysoft.android.budget.uikit.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import ru.bysoft.android.budget.uikit.R

internal val Ermilov = FontFamily(Font(R.font.ermilov))

internal val Roboto = FontFamily(Font(R.font.roboto))

internal val PublicSans = FontFamily(Font(R.font.public_sans))


object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,0.0f)
}