package ru.bysoft.android.budget.uikit.components.amount

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

class AmountFormatterTest {

    private val ru = AmountFormatter(Locale.forLanguageTag("ru"))
    private val en = AmountFormatter(Locale.US)

    @Test
    fun `ru groups with a thin space and separates the fraction with a comma`() {
        assertEquals("1 234,56", ru.format("1234.56"))
    }

    @Test
    fun `en groups with commas and separates the fraction with a point`() {
        assertEquals("1,234.56", en.format("1234.56"))
    }

    @Test
    fun `leading zeros collapse`() {
        assertEquals("7", en.format("007"))
    }

    @Test
    fun `a fraction is cut to two digits`() {
        assertEquals("1.23", en.format("1.239"))
    }

    @Test
    fun `empty is zero`() {
        assertEquals("0", en.format(""))
    }

    @Test
    fun `a typed trailing separator is kept so the fraction can follow`() {
        assertEquals("12.", en.format("12."))
    }

    @Test
    fun `a pasted en-style amount in ru keeps its fraction`() {
        assertEquals("1 234,5", ru.format("1.234,5"))
    }

    @Test
    fun `parse returns digits with a point fraction`() {
        assertEquals("1234.5", ru.parse("1 234,5"))
    }

    @Test
    fun `parse reads a lone point as the fraction mark in ru`() {
        assertEquals("1.5", ru.parse("1.5"))
    }

    @Test
    fun `parse drops a grouping comma in en`() {
        assertEquals("1234", en.parse("1,234"))
    }

    @Test
    fun `parse drops letters`() {
        assertEquals("12", en.parse("1a2"))
    }

    @Test
    fun `a point typed after a fraction keeps the fraction in en`() {
        assertEquals("12.5", en.parse("12.5."))
    }

    @Test
    fun `a point typed after a fraction keeps the fraction in ru`() {
        assertEquals("12.5", ru.parse("12.5."))
    }

    @Test
    fun `a comma typed after a point fraction keeps the fraction in ru`() {
        assertEquals("12.5", ru.parse("12.5,"))
    }

    @Test
    fun `a pasted ru-style amount in en keeps its fraction`() {
        assertEquals("1234.5", en.parse("1.234,5"))
    }

    @Test
    fun `parse keeps ascii digits only`() {
        assertEquals("1", en.parse("1٢"))
    }

    @Test
    fun `parse of empty is empty`() {
        assertEquals("", en.parse(""))
    }

    @Test
    fun `parse of a lone separator is a zero fraction`() {
        assertEquals("0.", en.parse("."))
    }
}
