package ru.bysoft.android.budget.common.util

import kotlin.coroutines.cancellation.CancellationException

/** Like [runCatching], but a cancelled coroutine is rethrown instead of becoming a failure. */
inline fun <T> runCatchingCancellable(block: () -> T): Result<T> {
    return runCatching(block).onFailure { error ->
        if (error is CancellationException) throw error
    }
}
