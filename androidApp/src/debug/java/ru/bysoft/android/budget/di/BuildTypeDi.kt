package ru.bysoft.android.budget.di

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.bysoft.android.budget.common.network.LOG_INTERCEPTORS_NAME

val DebugDi = module {
    single<List<Interceptor>>(named(LOG_INTERCEPTORS_NAME)) {
        listOf(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
    }
}

val buildTypeModules = listOf(DebugDi)
