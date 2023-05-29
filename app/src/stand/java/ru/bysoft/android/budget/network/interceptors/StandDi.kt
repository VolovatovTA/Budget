package ru.bysoft.android.budget.network.interceptors

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.bysoft.android.budget.common.network.authentificator.AuthenticationInterceptorRefreshToken
import ru.bysoft.android.budget.common.token.network.ITokenRefreshApi
import javax.inject.Named

const val MAIN_BASE_URL_NAME = "mainBaseUrl"
const val WALLET_BASE_URL_NAME = "walletBaseUrl"
const val AUTH_INTERCEPTOR_NAME = "authInterceptor"
const val AUTH_CLIENT_NAME = "clientWithAuth"
const val NO_AUTH_CLIENT_NAME = "clientWithoutAuth"

@Module
@InstallIn(SingletonComponent::class)
abstract class StandDi {

    companion object {

        @Provides
        @Named(MAIN_BASE_URL_NAME)
        fun provideMainBaseUrl(): String = "https://it-bears.com"
        @Provides
        @Named(WALLET_BASE_URL_NAME)
        fun provideWalletBaseUrl(): String = "https://wallet.it-bears.com"

        @Provides
        fun provideRefreshApi(
            @Named(MAIN_BASE_URL_NAME) baseUrl: String,
            @Named(NO_AUTH_CLIENT_NAME) client: OkHttpClient
        ): ITokenRefreshApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ITokenRefreshApi::class.java)

        @Provides
        @Named(AUTH_CLIENT_NAME)
        fun provideAuthClient(
            set: Set<@JvmSuppressWildcards Interceptor>,
            @Named(AUTH_INTERCEPTOR_NAME) authInterceptor: Interceptor
        ): OkHttpClient {
            val clientBuilder = OkHttpClient.Builder()
            set.forEach { clientBuilder.addInterceptor(it) }
            clientBuilder.addInterceptor(authInterceptor)
            return clientBuilder.build()
        }

        @Provides
        @Named(NO_AUTH_CLIENT_NAME)
        fun provideNoAuthClient(
            set: Set<@JvmSuppressWildcards Interceptor>
        ): OkHttpClient {
            val clientBuilder = OkHttpClient.Builder()
            set.forEach { clientBuilder.addInterceptor(it) }
            return clientBuilder.build()
        }

        @Provides
        @IntoSet
        fun provideLoggerInterceptor(): Interceptor = HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
    }


    @Binds
    @Named(AUTH_INTERCEPTOR_NAME)
    abstract fun provide(authInterceptor: AuthenticationInterceptorRefreshToken): Interceptor

}