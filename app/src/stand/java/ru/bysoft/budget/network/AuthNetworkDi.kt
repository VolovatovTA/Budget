package ru.bysoft.budget.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import ru.bysoft.budget.auth.data.network.IAuthApi
import ru.bysoft.budget.common.network.MAIN_BASE_URL_NAME
import ru.bysoft.budget.common.network.RetrofitClient
import ru.bysoft.budget.network.interceptors.LoggerIntercepor
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthNetworkDi {
    companion object {
        @Provides
        fun provideAuthApi(
            @Named(MAIN_BASE_URL_NAME) baseUrl: String,
            set: Set<@JvmSuppressWildcards Interceptor>
        ): IAuthApi =
            RetrofitClient.getApi(baseUrl, set).create(IAuthApi::class.java)

        @Provides
        @Named(MAIN_BASE_URL_NAME)
        fun provideBaseUrl(): String = "http://bysoft.ru/"

        @Provides
        @IntoSet
        fun provideInterceptors(): Interceptor =
            LoggerIntercepor()

    }
}