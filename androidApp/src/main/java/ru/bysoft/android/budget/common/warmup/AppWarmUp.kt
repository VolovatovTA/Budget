package ru.bysoft.android.budget.common.warmup

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.features.currency_rates.data.ICurrencyRatesRepo

/**
 * Everything that is worth loading at process start so the first screens open with data.
 * Runs once from [ru.bysoft.android.budget.common.BudgetApplication]; failures are logged, never shown.
 */
class AppWarmUp(
    private val currencyRatesRepo: ICurrencyRatesRepo,
    private val errorLogger: IErrorLogger,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
) {
    private val handler = CoroutineExceptionHandler { _, t -> errorLogger.logError(t) }

    fun start() {
        scope.launch(handler) {
            // fills the Room cache while the splash animation plays
            currencyRatesRepo.getCurrencyRates()
        }
    }
}
