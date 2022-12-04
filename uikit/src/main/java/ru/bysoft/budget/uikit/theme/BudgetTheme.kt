package ru.bysoft.budget.uikit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import ru.bysoft.budget.R
import ru.bysoft.budget.uikit.colors.UiKitColors.colors


internal val LightColors: Colors
    @Composable
    get() = lightColors(
        primary = colors.col1,
        primaryVariant = colors.col2,
        secondary = colors.col3,
        secondaryVariant = colors.blue123,
        background = colors.colE,
        error = colors.red,
        onPrimary = colors.col7,
        onSurface = colors.col5,
        onError = colors.col4
    )

internal val DarkColors: Colors
    @Composable
    get() = darkColors(
        primary = colors.col1,
        primaryVariant = colors.col2,
        secondary = colors.col3,
        secondaryVariant = colors.blue123,
        background = colors.colE,
        error = colors.red,
        onPrimary = colors.white,
        onSurface = colors.black,
        onError = colors.white
    )

internal val Custom1Colors: Colors
    @Composable
    get() = customColors(
        primary = colors.col1,
        primaryVariant = colors.col2,
        secondary = colors.col3,
        secondaryVariant = colors.blue123,
        background = colors.white,
        error = colors.red,
        onPrimary = colors.white,
        onSurface = colors.black,
        onError = colors.white
    )

internal val Ermilov = FontFamily(
    Font(R.font.ermilov)
)

internal val Roboto = FontFamily(
    Font(R.font.roboto)
)

enum class BudgetThemes {
    CUSTOM1
}

fun customColors(
    primary: Color = Color(0xFF6200EE),
    primaryVariant: Color = Color(0xFF3700B3),
    secondary: Color = Color(0xFF03DAC6),
    secondaryVariant: Color = Color(0xFF018786),
    background: Color = Color.White,
    surface: Color = Color.White,
    error: Color = Color(0xFFB00020),
    onPrimary: Color = Color.White,
    onSecondary: Color = Color.Black,
    onBackground: Color = Color.Black,
    onSurface: Color = Color.Black,
    onError: Color = Color.White
): Colors = Colors(
    primary,
    primaryVariant,
    secondary,
    secondaryVariant,
    background,
    surface,
    error,
    onPrimary,
    onSecondary,
    onBackground,
    onSurface,
    onError,
    true
)


object BudgetTheme {


    @Composable
    fun BudgetTheme(
        content: @Composable () -> Unit
    ) {
        MaterialTheme(
            colors = getColorsByTheme(),
            content = content
        )
    }

    @Composable
    private fun getColorsByTheme(theme: BudgetThemes? = null): Colors {
        return when (theme) {
            BudgetThemes.CUSTOM1 -> Custom1Colors
            null -> getColorsBySystemTheme()
        }
    }

    @Composable
    private fun getColorsBySystemTheme(): Colors {
        return if (isSystemInDarkTheme()) DarkColors else LightColors
    }
}