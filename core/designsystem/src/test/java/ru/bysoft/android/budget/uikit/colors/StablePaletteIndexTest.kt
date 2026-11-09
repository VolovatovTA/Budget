package ru.bysoft.android.budget.uikit.colors

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class StablePaletteIndexTest {

    @Test
    fun `known value is pinned so persisted colours never change between releases`() {
        assertEquals(6, stablePaletteIndex("abc", 7))
    }

    @Test
    fun `index is within range for empty, ascii and cyrillic ids`() {
        assertTrue(listOf("", "wallet-42", "категория").all { stablePaletteIndex(it, 7) in 0 until 7 })
    }

    @Test
    fun `seven consecutive ids get seven different slots`() {
        assertEquals(7, (1..7).map { stablePaletteIndex("id$it", 7) }.toSet().size)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `empty palette is rejected`() {
        stablePaletteIndex("w1", 0)
    }
}
