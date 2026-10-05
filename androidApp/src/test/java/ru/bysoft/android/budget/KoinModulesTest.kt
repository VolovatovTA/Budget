package ru.bysoft.android.budget

import android.content.Context
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.test.verify.verify
import ru.bysoft.android.budget.common.appModules

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class KoinModulesTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun allDefinitionsResolve() {
        module { includes(appModules) }.verify(
            extraTypes = listOf(Context::class)
        )
    }
}