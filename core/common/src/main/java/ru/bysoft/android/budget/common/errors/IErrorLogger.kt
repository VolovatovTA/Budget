package ru.bysoft.android.budget.common.errors

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

interface IErrorLogger {
    fun logError(t: Throwable)
}

class AndroidErrorLogger : IErrorLogger {
    override fun logError(t: Throwable) {
        Log.e("error", "", t)
    }
}

/**
 * A [CoroutineExceptionHandler] that logs the failure through this logger and then runs [andThen],
 * typically to put the screen into an error state:
 *
 *     private val handler = errorLogger.logAnd { state.value = ErrorState }
 *     viewModelScope.launch(handler) { … }
 */
fun IErrorLogger.handler(andThen: (Throwable) -> Unit = {}): CoroutineExceptionHandler =
    CoroutineExceptionHandler { _, t ->
        logError(t)
        andThen(t)
    }
