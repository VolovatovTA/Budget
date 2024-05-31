package ru

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import ru.bysoft.android.budget.common.errors.ErrorLogger
import ru.bysoft.android.budget.common.errors.IErrorLogger
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DebugDi {
    companion object {
        @Provides
        @IntoSet
        fun provideLoggerInterceptor(): Interceptor = HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
    }

    @Binds
    @Singleton
    abstract fun bindErrorLogger(errorLogger: ErrorLogger): IErrorLogger
}