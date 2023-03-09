package ru.bysoft.android.budget.uikit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import ru.bysoft.android.budget.uikit.R
import ru.bysoft.android.budget.uikit.colors.UiKitColors.colors

internal val LightColors: Colors
    @Composable
    get() = lightColors(
        primary = colors.light,
        primaryVariant = colors.col1,
        secondary = colors.col4,
        secondaryVariant = colors.col4_inactive,
        background = colors.light,
        error = colors.red,
        onPrimary = colors.dark40,
        onSurface = colors.col5,
        onError = colors.col4
    )

internal val DarkColors: Colors
    @Composable
    get() = darkColors(
        primary = colors.light,
        primaryVariant = colors.col1,
        secondary = colors.col4,
        secondaryVariant = colors.col4_inactive,
        background = colors.light40,
        error = colors.red,
        onPrimary = colors.dark,
        onSurface = colors.col5,
        onError = colors.col4
    )

internal val Custom1Colors: Colors
    @Composable
    get() = customColors(
        primary = colors.light,
        primaryVariant = colors.col1,
        secondary = colors.col4,
        secondaryVariant = colors.col4_inactive,
        background = colors.light,
        error = colors.red,
        onPrimary = colors.dark,
        onSurface = colors.col5,
        onError = colors.col4
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