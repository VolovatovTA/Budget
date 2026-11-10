package ru.bysoft.android.budget.common

import org.junit.Assert.assertSame
import org.junit.Test
import ru.bysoft.android.budget.common.util.runCatchingCancellable
import kotlin.coroutines.cancellation.CancellationException

class RunCatchingCancellableTest {

    @Test(expected = CancellationException::class)
    fun `rethrows cancellation`() {
        runCatchingCancellable { throw CancellationException("cancelled") }
    }

    @Test
    fun `wraps another throwable into failure`() {
        val error = IllegalStateException("boom")

        val result = runCatchingCancellable { throw error }

        assertSame(error, result.exceptionOrNull())
    }
}
