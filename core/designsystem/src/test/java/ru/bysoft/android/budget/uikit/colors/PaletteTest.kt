package ru.bysoft.android.budget.uikit.colors

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PaletteTest {

    @Test
    fun `palettes report their own darkness and carry their token set`() {
        assertTrue(DarkPalette.isDark)
        assertFalse(LightPalette.isDark)
        assertEquals(DarkTokens, DarkPalette.tokens)
        assertEquals(LightTokens, LightPalette.tokens)
    }
}
