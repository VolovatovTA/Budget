package ru.bysoft.budget.network.interceptors

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import ru.bysoft.budget.common.network.INTERCEPTORS_LIST_NAME
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class StandDi {
    @Provides
    @Named(INTERCEPTORS_LIST_NAME)
    fun provideInterceptors(): List<Interceptor> =
        listOf(
            LoggerIntercepor()
        )
}