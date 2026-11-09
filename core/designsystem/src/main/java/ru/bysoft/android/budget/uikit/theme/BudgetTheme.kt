package ru.bysoft.android.budget.uikit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.lightColors
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import ru.bysoft.android.budget.uikit.R
import ru.bysoft.android.budget.uikit.colors.DarkPalette
import ru.bysoft.android.budget.uikit.colors.LightPalette
import ru.bysoft.android.budget.uikit.colors.LocalBudgetPalette
import ru.bysoft.android.budget.uikit.colors.Palette
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import androidx.compose.material.MaterialTheme as Material2Theme
import androidx.compose.material3.MaterialTheme as Material3Theme

/**
 * Wix Madefor Display (SIL OFL 1.1, see core/designsystem/FONT_LICENSE.txt): one variable TTF,
 * weight axis 400..800, Latin + Cyrillic + ₽ €. Instances are declared explicitly so [FontWeight]
 * lookups resolve to the matching axis value instead of synthetic bold.
 */
@OptIn(ExperimentalTextApi::class)
internal val BudgetFontFamily = FontFamily(
    listOf(400, 500, 600, 700, 800).map { weight ->
        // Explicit axis value: relying on the ResourceFont default left every weight rendering
        // as 400 on a Redmi Note 8 Pro / Android 11 (checked 2026-11-09).
        Font(
            resId = R.font.wix_madefor_display,
            weight = FontWeight(weight),
            variationSettings = FontVariation.Settings(FontVariation.weight(weight)),
        )
    }
)

/**
 * Root theme. Chooses the palette once ([darkTheme], system by default; the settings override
 * arrives in a later PR) and feeds it to three consumers: the kit itself through
 * [LocalBudgetPalette], Material 3 primitives through a [ColorScheme] built from the tokens, and
 * the Material 2 widgets still used by features through the legacy mapping, so nothing changes
 * colour while the migration is in progress.
 */
@Composable
fun BudgetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val palette: Palette = if (darkTheme) DarkPalette else LightPalette
    CompositionLocalProvider(LocalBudgetPalette provides palette) {
        Material3Theme(colorScheme = palette.toColorScheme(darkTheme)) {
            Material2Theme(
                colors = lightColors(
                    primary = palette.surface.primary,
                    surface = palette.surface.primary,
                    background = palette.surface.primary,
                )
            ) {
                CompositionLocalProvider(LocalTextSelectionColors provides UiKitColors.selectionColors) {
                    content()
                }
            }
        }
    }
}

/**
 * Token → Material 3 mapping. `background`/`surface` are the screen ground (Scaffold, full-screen
 * surfaces), the `surfaceContainer*` slots are raised blocks (cards, sheets, navigation bar).
 * Every slot an M3 widget reads is set so no baseline purple can leak in.
 */
internal fun Palette.toColorScheme(dark: Boolean): ColorScheme {
    val t = tokens
    val base = if (dark) darkColorScheme() else lightColorScheme()
    return base.copy(
        primary = t.accent,
        onPrimary = Color.White,
        primaryContainer = t.accentSoft,
        onPrimaryContainer = t.accent,
        inversePrimary = t.accent,
        secondary = t.textMedium,
        onSecondary = t.background,
        secondaryContainer = t.surface2,
        onSecondaryContainer = t.textHigh,
        tertiary = t.positive,
        onTertiary = t.background,
        tertiaryContainer = t.surface2,
        onTertiaryContainer = t.textHigh,
        background = t.background,
        onBackground = t.textHigh,
        surface = t.background,
        onSurface = t.textHigh,
        surfaceVariant = t.surface,
        onSurfaceVariant = t.textMedium,
        surfaceTint = t.accent,
        inverseSurface = t.textHigh,
        inverseOnSurface = t.background,
        surfaceContainerLowest = t.surface,
        surfaceContainerLow = t.surface,
        surfaceContainer = t.surface,
        surfaceContainerHigh = t.surface2,
        surfaceContainerHighest = t.surface2,
        outline = t.line,
        outlineVariant = t.line,
        error = t.negative,
        onError = Color.White,
        errorContainer = t.negative.copy(alpha = 0.18f),
        onErrorContainer = t.negative,
        scrim = Color.Black,
    )
}
