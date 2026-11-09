package ru.bysoft.android.budget.uikit.theme

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.bysoft.android.budget.uikit.colors.DarkPalette
import ru.bysoft.android.budget.uikit.colors.LightPalette

class ColorSchemeMappingTest {

    @Test
    fun `dark scheme takes every brand slot from the tokens, nothing stays baseline purple`() {
        val t = DarkPalette.tokens
        val scheme = DarkPalette.toColorScheme(dark = true)
        assertEquals(t.accent, scheme.primary)
        assertEquals(t.accent, scheme.surfaceTint)
        assertEquals(t.accent, scheme.inversePrimary)
        assertEquals(t.background, scheme.background)
        assertEquals(t.background, scheme.surface)
        assertEquals(t.surface, scheme.surfaceContainerLow)
        assertEquals(t.surface2, scheme.surfaceContainerHigh)
        assertEquals(t.textHigh, scheme.onSurface)
        assertEquals(t.textMedium, scheme.onSurfaceVariant)
        assertEquals(t.line, scheme.outline)
        assertEquals(t.negative, scheme.error)
        assertEquals(Color.Black, scheme.scrim)
    }

    @Test
    fun `light scheme maps the same slots from the light tokens`() {
        val t = LightPalette.tokens
        val scheme = LightPalette.toColorScheme(dark = false)
        assertEquals(t.accent, scheme.primary)
        assertEquals(t.background, scheme.background)
        assertEquals(t.surface, scheme.surfaceContainer)
        assertEquals(t.textHigh, scheme.onBackground)
    }
}
