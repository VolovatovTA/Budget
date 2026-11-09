package ru.bysoft.android.budget.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withAnnotationNamed
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

/**
 * A test checks one behaviour. More than two assertions (`assert*`, `verify*`, `coVerify*`) means
 * it checks several things at once and hides which one broke: split it, or compare one value object.
 */
class TestShapeTest {

    @Test
    fun `a test makes at most two assertions`() {
        Konsist
            .scopeFromTest()
            .functions()
            .withAnnotationNamed("Test")
            .assertTrue(additionalMessage = "split the test or assert on one value object") {
                ASSERTION.findAll(it.text).count() <= MAX_ASSERTIONS
            }
    }

    private companion object {
        const val MAX_ASSERTIONS = 2
        val ASSERTION = Regex("""\b(assert\w*|verify\w*|coVerify\w*)\s*[({]""")
    }
}
