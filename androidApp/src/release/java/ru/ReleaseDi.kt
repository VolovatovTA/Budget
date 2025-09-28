package ru

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ElementsIntoSet
import okhttp3.Interceptor
import ru.bysoft.android.budget.common.errors.IErrorLogger

@Module
@InstallIn(SingletonComponent::class)
class ReleaseDi {
    @Provides
    @ElementsIntoSet
    fun provideLoggerInterceptor(): Set<@JvmSuppressWildcards Interceptor> = emptySet()

    @Provides
    fun bindErrorLogger(): IErrorLogger = object : IErrorLogger {
        override fun logError(t: Throwable) = Unit
    }
}