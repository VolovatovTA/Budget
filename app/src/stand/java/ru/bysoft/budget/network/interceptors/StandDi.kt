package ru.bysoft.budget.network.interceptors

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.bysoft.budget.common.network.authentificator.AuthenticationInterceptorRefreshToken
import ru.bysoft.budget.common.token.network.ITokenRefreshApi
import javax.inject.Named

const val MAIN_BASE_URL_NAME = "mainBaseUrl"
const val INTERCEPTORS_SET_NAME = "setInterceptors"
const val AUTH_INTERCEPTOR_NAME = "authInterceptor"
const val AUTH_CLIENT_NAME = "clientWithAuth"
const val NO_AUTH_CLIENT_NAME = "clientWithoutAuth"

@Module
@InstallIn(SingletonComponent::class)
abstract class StandDi {

    companion object {

        @Provides
        @Named(MAIN_BASE_URL_NAME)
        fun provideBaseUrl(): String = "https://bysoft.ru/"

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
            //ADD DISPATCHER WITH MAX REQUEST TO 1
            val dispatcher = Dispatcher()
//            dispatcher.maxRequests = 1
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
                level = HttpLoggingInterceptor.Level.BODY
            }
    }


    @Binds
    @Named(AUTH_INTERCEPTOR_NAME)
    abstract fun provide(authInterceptor: AuthenticationInterceptorRefreshToken): Interceptor


}