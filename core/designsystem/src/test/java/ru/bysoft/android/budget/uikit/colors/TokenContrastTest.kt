package ru.bysoft.android.budget.uikit.colors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * WCAG 2 contrast of the token tables, so a colour tweak that makes text or a category chip
 * unreadable in one of the themes fails here instead of on a user's screen.
 */
class TokenContrastTest {

    @Test
    fun `text tokens reach 4,5 to 1 on every surface in both themes`() {
        val violations = themes.flatMap { (theme, t) ->
            listOf("textHigh" to t.textHigh, "textMedium" to t.textMedium).flatMap { (name, fg) ->
                surfacesOf(t).mapNotNull { (surface, bg) -> violation(theme, name, surface, fg, bg, TEXT_MIN) }
            }
        }
        assertEquals(emptyList<String>(), violations)
    }

    @Test
    fun `accent, semantic and category colours reach 3 to 1 on the surfaces they are drawn on`() {
        val violations = themes.flatMap { (theme, t) ->
            val semantic = listOf("accent" to t.accent, "positive" to t.positive, "negative" to t.negative, "warning" to t.warning)
            val marks = semantic + t.categoryPalette.mapIndexed { i, c -> "category[$i]" to c }
            marks.flatMap { (name, fg) ->
                surfacesOf(t).mapNotNull { (surface, bg) -> violation(theme, name, surface, fg, bg, GRAPHIC_MIN) }
            }
        }
        assertEquals(emptyList<String>(), violations)
    }

    private fun surfacesOf(t: BudgetTokens) =
        listOf("background" to t.background, "surface" to t.surface, "surface2" to t.surface2)

    private fun violation(theme: String, name: String, surface: String, fg: Color, bg: Color, min: Double): String? {
        val ratio = contrastRatio(fg, bg)
        return if (ratio < min) "$theme $name on $surface = %.2f".format(ratio) else null
    }

    private val themes = listOf("dark" to DarkTokens, "light" to LightTokens)

    private companion object {
        const val TEXT_MIN = 4.5
        const val GRAPHIC_MIN = 3.0

        fun contrastRatio(a: Color, b: Color): Double {
            val la = a.luminance().toDouble()
            val lb = b.luminance().toDouble()
            return (maxOf(la, lb) + 0.05) / (minOf(la, lb) + 0.05)
        }
    }
}
