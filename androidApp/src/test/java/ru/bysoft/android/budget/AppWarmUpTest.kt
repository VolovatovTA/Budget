package ru.bysoft.android.budget

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.bysoft.android.budget.common.data_entity.CurrencyRate
import ru.bysoft.android.budget.common.data_entity.CurrencyRateData
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.common.warmup.AppWarmUp
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.features.currency_rates.data.ICurrencyRatesRepo

@OptIn(ExperimentalCoroutinesApi::class)
class AppWarmUpTest {

    private class FakeRatesRepo(private val failWith: Throwable? = null) : ICurrencyRatesRepo {
        var calls = 0
        override suspend fun getCurrencyRates(): CurrencyRateData {
            calls++
            failWith?.let { throw it }
            return CurrencyRateData(emptyMap())
        }
        override suspend fun getCurrencyRate(base: BudgetCurrencyEnum, target: BudgetCurrencyEnum) =
            CurrencyRate(target, 1.0)
    }

    private class FakeLogger : IErrorLogger {
        val errors = mutableListOf<Throwable>()
        override fun logError(t: Throwable) { errors += t }
    }

    @Test
    fun `start loads currency rates once`() = runTest {
        val repo = FakeRatesRepo()
        val logger = FakeLogger()
        AppWarmUp(repo, logger, warmUpScope()).start()
        testScheduler.advanceUntilIdle()

        assertEquals(1, repo.calls)
        assertEquals(emptyList<Throwable>(), logger.errors)
    }

    @Test
    fun `a failed warm-up is logged, not thrown`() = runTest {
        val boom = IllegalStateException("no network")
        val repo = FakeRatesRepo(failWith = boom)
        val logger = FakeLogger()
        AppWarmUp(repo, logger, warmUpScope()).start()
        testScheduler.advanceUntilIdle()

        assertEquals(listOf<Throwable>(boom), logger.errors)
    }

    // Same shape as the production scope: a SupervisorJob root, so the exception handler
    // sees failures instead of the test's own job. Only the dispatcher is swapped.
    private fun kotlinx.coroutines.test.TestScope.warmUpScope() =
        CoroutineScope(SupervisorJob() + StandardTestDispatcher(testScheduler))
}
