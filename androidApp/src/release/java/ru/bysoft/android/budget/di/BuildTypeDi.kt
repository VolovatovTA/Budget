package ru.bysoft.android.budget.di

import okhttp3.Interceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.bysoft.android.budget.common.network.LOG_INTERCEPTORS_NAME

val ReleaseDi = module {
    single<List<Interceptor>>(named(LOG_INTERCEPTORS_NAME)) { emptyList() }
}

val buildTypeModules = listOf(ReleaseDi)
