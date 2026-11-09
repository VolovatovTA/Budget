package ru.bysoft.android.budget.uikit.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.bysoft.android.budget.uikit.colors.BudgetTokens
import ru.bysoft.android.budget.uikit.colors.DarkPalette
import ru.bysoft.android.budget.uikit.colors.LightPalette

/** A slot left unmapped would render in Material's baseline purple; this pins every slot a kit widget reads. */
class ColorSchemeMappingTest {

    @Test
    fun `dark scheme takes every slot from the dark tokens`() {
        assertEquals(expectedSlots(DarkPalette.tokens), slotsOf(DarkPalette.toColorScheme(dark = true)))
    }

    @Test
    fun `light scheme takes every slot from the light tokens`() {
        assertEquals(expectedSlots(LightPalette.tokens), slotsOf(LightPalette.toColorScheme(dark = false)))
    }

    private fun expectedSlots(t: BudgetTokens) = mapOf(
        "primary" to t.accent,
        "surfaceTint" to t.accent,
        "inversePrimary" to t.accent,
        "background" to t.background,
        "surface" to t.background,
        "onBackground" to t.textHigh,
        "onSurface" to t.textHigh,
        "onSurfaceVariant" to t.textMedium,
        "surfaceContainer" to t.surface,
        "surfaceContainerLow" to t.surface,
        "surfaceContainerHigh" to t.surface2,
        "outline" to t.line,
        "error" to t.negative,
        "scrim" to Color.Black,
    )

    private fun slotsOf(s: ColorScheme) = mapOf(
        "primary" to s.primary,
        "surfaceTint" to s.surfaceTint,
        "inversePrimary" to s.inversePrimary,
        "background" to s.background,
        "surface" to s.surface,
        "onBackground" to s.onBackground,
        "onSurface" to s.onSurface,
        "onSurfaceVariant" to s.onSurfaceVariant,
        "surfaceContainer" to s.surfaceContainer,
        "surfaceContainerLow" to s.surfaceContainerLow,
        "surfaceContainerHigh" to s.surfaceContainerHigh,
        "outline" to s.outline,
        "error" to s.error,
        "scrim" to s.scrim,
    )
}
