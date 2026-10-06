package ru.bysoft.android.budget

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.test.verify.verify
import ru.bysoft.android.budget.common.appModules

/** Проверяет, что у каждого класса в графе Koin все зависимости зарегистрированы */
class KoinModulesTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun allDefinitionsResolve() {
        module { includes(appModules) }.verify(
            // Context даёт androidContext(), SavedStateHandle Koin подставляет во view model сам
            extraTypes = listOf(Context::class, SavedStateHandle::class)
        )
    }
}