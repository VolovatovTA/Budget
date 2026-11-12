package ru.bysoft.android.budget.uikit.components.amount

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

class AmountVisualTransformationTest {

    private val typed = Color.Black
    private val placeholder = Color.Gray
    private val transformation =
        AmountVisualTransformation(AmountFormatter(Locale.US), typed, placeholder)

    private fun shown(raw: String) = transformation.filter(AnnotatedString(raw)).text

    @Test
    fun `an integer gets a placeholder fraction of two zeros`() {
        assertEquals("12.00", shown("12").text)
    }

    @Test
    fun `a one digit fraction gets one placeholder zero`() {
        assertEquals("12.50", shown("12.5").text)
    }

    @Test
    fun `a full fraction gets no placeholder`() {
        assertEquals("12.56", shown("12.56").text)
    }

    @Test
    fun `typed characters use the typed colour`() {
        val styles = shown("12").spanStyles
        assertEquals(typed, styles.first().item.color)
    }

    @Test
    fun `the placeholder uses the placeholder colour`() {
        val styles = shown("12").spanStyles
        assertEquals(placeholder, styles.last().item.color)
    }
}
