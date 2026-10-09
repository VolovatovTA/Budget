package ru.bysoft.android.budget.uikit.theme

import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import ru.bysoft.android.budget.uikit.R
import ru.bysoft.android.budget.uikit.colors.UiKitColors

internal val PublicSans = FontFamily(Font(R.font.public_sans))

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