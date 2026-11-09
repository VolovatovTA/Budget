package ru.bysoft.android.budget.uikit.theme

import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import ru.bysoft.android.budget.uikit.R
import ru.bysoft.android.budget.uikit.colors.UiKitColors

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

@Composable
fun BudgetTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors(
            primary = UiKitColors.colors.surface.primary,
            surface = UiKitColors.colors.surface.primary,
            background = UiKitColors.colors.surface.primary,
        )
    ){
        CompositionLocalProvider(LocalTextSelectionColors provides UiKitColors.selectionColors) {
            content()
        }
    }
}