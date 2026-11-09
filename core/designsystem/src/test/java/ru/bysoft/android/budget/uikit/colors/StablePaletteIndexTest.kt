package ru.bysoft.android.budget.uikit.colors

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class StablePaletteIndexTest {

    @Test
    fun `same id gives same index`() {
        assertEquals(stablePaletteIndex("w1", 7), stablePaletteIndex("w1", 7))
    }

    @Test
    fun `index is within range`() {
        for (id in listOf("", "a", "wallet-42", "категория")) {
            assertTrue(stablePaletteIndex(id, 7) in 0 until 7)
        }
    }

    @Test
    fun `known value is pinned so persisted colours never change`() {
        // FNV-1a 32-bit over UTF-8 bytes of "abc", masked to 31 bits, modulo 7
        assertEquals(5, stablePaletteIndex("abc", 7))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `empty palette is rejected`() {
        stablePaletteIndex("w1", 0)
    }

    @Test
    fun `seven ids are not all the same colour`() {
        val indices = (1..7).map { stablePaletteIndex("id$it", 7) }.toSet()
        assertTrue("got $indices", indices.size > 1)
    }
}
