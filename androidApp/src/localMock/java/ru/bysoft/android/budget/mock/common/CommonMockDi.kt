package ru.bysoft.android.budget.mock.common

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.bysoft.android.budget.common.network.MOCK_DELAY_NAME

val CommonMockDi = module {
    single<Long>(named(MOCK_DELAY_NAME)) { 1000L }
}