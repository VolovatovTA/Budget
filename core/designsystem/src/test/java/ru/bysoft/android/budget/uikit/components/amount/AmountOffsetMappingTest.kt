package ru.bysoft.android.budget.uikit.components.amount

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

class AmountOffsetMappingTest {

    private val formatter = AmountFormatter(Locale.US)
    private val mapping = AmountOffsetMapping("1,234", 4, formatter)

    @Test
    fun `the cursor after the second digit skips the grouping comma`() {
        assertEquals(3, mapping.originalToTransformed(2))
    }

    @Test
    fun `a cursor in the placeholder maps to the end of the raw text`() {
        assertEquals(4, mapping.transformedToOriginal(8))
    }
}
