package ru.bysoft.android.budget.common.errors

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

interface IErrorLogger {
    fun logError(t: Throwable)
}

class ErrorLogger @Inject constructor() : IErrorLogger {
    override fun logError(t: Throwable) {
        Log.e("error", "", t)
    }
}

val errorLogger = ErrorLogger()

val exceptionHandler: CoroutineExceptionHandler
    get() = CoroutineExceptionHandler { _, t -> errorLogger.logError(t) }