package ru.bysoft.android.budget.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withParentNamed
import com.lemonappdev.konsist.api.verify.assertFalse
import org.junit.Test

/**
 * Coroutines started from a ViewModel must not be able to crash the app or lose an error.
 * Every `viewModelScope.launch` needs a context argument (an `errorLogger.handler(...)`), and
 * `viewModelScope.async` is not allowed as a root coroutine: its failure only surfaces at `await()`,
 * so start it inside a `launch(handler)` instead.
 */
class ViewModelCoroutinesTest {

    private val viewModels = Konsist
        .scopeFromProduction()
        .classes()
        .withParentNamed("ViewModel", indirectParents = true)

    @Test
    fun `viewModelScope launch always gets an exception handler`() {
        viewModels.assertFalse(additionalMessage = "use viewModelScope.launch(errorLogger.handler { ... })") {
            LAUNCH_WITHOUT_CONTEXT.containsMatchIn(it.text)
        }
    }

    @Test
    fun `viewModelScope async is started inside a launch, never as a root`() {
        viewModels.assertFalse(additionalMessage = "wrap it: viewModelScope.launch(handler) { async { ... }.await() }") {
            ROOT_ASYNC.containsMatchIn(it.text)
        }
    }

    private companion object {
        /** `viewModelScope.launch {` with nothing between the parentheses or no parentheses at all. */
        val LAUNCH_WITHOUT_CONTEXT = Regex("""viewModelScope\.launch\s*(\(\s*\))?\s*\{""")
        val ROOT_ASYNC = Regex("""viewModelScope\.async\b""")
    }
}
