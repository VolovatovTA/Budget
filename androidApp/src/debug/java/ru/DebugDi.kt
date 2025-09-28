package ru


import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.bysoft.android.budget.common.errors.ErrorLogger
import ru.bysoft.android.budget.common.errors.IErrorLogger


val DebugDi = module {
    single<Interceptor>{
        HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
    }

    singleOf(::ErrorLogger) bind IErrorLogger::class
}