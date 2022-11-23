package ru.bysoft.budget.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.bysoft.budget.auth.data.network.IAuthApi
import ru.bysoft.budget.network.interceptors.LoggerIntercepor
import ru.bysoft.budget.network.interceptors.MAIN_BASE_URL_NAME
import ru.bysoft.budget.network.interceptors.NO_AUTH_CLIENT_NAME
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthNetworkDi {
    companion object {
        @Provides
        fun provideAuthApi(
            @Named(MAIN_BASE_URL_NAME) baseUrl: String,
            @Named(NO_AUTH_CLIENT_NAME) client: OkHttpClient
        ): IAuthApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .callbackExecutor {
                it.run()
            }
            .build()
            .create(IAuthApi::class.java)

        @Provides
        @IntoSet
        fun provideInterceptors(): Interceptor =
            LoggerIntercepor()

    }
}