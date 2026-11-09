package ru.bysoft.android.budget.uikit.colors

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BudgetTokensTest {

    @Test
    fun `category palette follows the spec order`() {
        val expected = listOf(0xFF3DCB8F, 0xFFFF6B6B, 0xFF7C6CFF, 0xFFF5B942, 0xFF19A3A3, 0xFFFF8FB1, 0xFF5BA8FF)
            .map { Color(it) }
        assertEquals(expected, DarkTokens.categoryPalette)
    }

    @Test
    fun `light and dark share entity colours so an id looks the same in both themes`() {
        assertEquals(DarkTokens.categoryPalette, LightTokens.categoryPalette)
        assertEquals(DarkTokens.walletGradients, LightTokens.walletGradients)
    }

    @Test
    fun `wallet gradients are at least four two-stop gradients starting with violet and teal`() {
        assertTrue(DarkTokens.walletGradients.size >= 4)
        assertTrue(DarkTokens.walletGradients.all { it.size == 2 })
        assertEquals(listOf(Color(0xFF6B5BFF), Color(0xFFB48CFF)), DarkTokens.walletGradients[0])
        assertEquals(listOf(Color(0xFF0E7C86), Color(0xFF19A3A3)), DarkTokens.walletGradients[1])
    }

    @Test
    fun `accent soft is the accent at low alpha`() {
        assertEquals(0.18f, DarkTokens.accentSoft.alpha, 0.001f)
        assertEquals(DarkTokens.accent.copy(alpha = 0.18f), DarkTokens.accentSoft)
        assertEquals(LightTokens.accent.copy(alpha = LightTokens.accentSoft.alpha), LightTokens.accentSoft)
    }
}
