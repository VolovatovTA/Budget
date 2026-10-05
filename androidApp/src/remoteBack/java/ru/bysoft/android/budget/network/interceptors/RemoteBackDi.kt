package ru.bysoft.android.budget.network.interceptors

//import dagger.Binds
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.bysoft.android.budget.common.network.authentificator.AuthenticationInterceptorRefreshToken
import ru.bysoft.android.budget.common.token.network.ITokenRefreshApi
import java.util.concurrent.TimeUnit
import javax.inject.Named

const val MAIN_BASE_URL_NAME = "mainBaseUrl"
const val WALLET_BASE_URL_NAME = "walletBaseUrl"
const val AUTH_INTERCEPTOR_NAME = "authInterceptor"
const val AUTH_CLIENT_NAME = "clientWithAuth"
const val NO_AUTH_CLIENT_NAME = "clientWithoutAuth"

const val TIME_OUT_MINUTES = 2L

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteBackDi {

    companion object {

        @Provides
        @Named(MAIN_BASE_URL_NAME)
        fun provideMainBaseUrl(): String = "https://imbsoft.tech"

        @Provides
        @Named(WALLET_BASE_URL_NAME)
        fun provideWalletBaseUrl(): String = "https://imbsoft.tech"

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
            return clientBuilder
                .callTimeout(TIME_OUT_MINUTES, TimeUnit.MINUTES)
                .readTimeout(TIME_OUT_MINUTES, TimeUnit.MINUTES)
                .writeTimeout(TIME_OUT_MINUTES, TimeUnit.MINUTES)
                .connectTimeout(TIME_OUT_MINUTES, TimeUnit.MINUTES)
                .build()
        }

        @Provides
        @Named(NO_AUTH_CLIENT_NAME)
        fun provideNoAuthClient(
            set: Set<@JvmSuppressWildcards Interceptor>
        ): OkHttpClient {
            val clientBuilder = OkHttpClient.Builder()
            set.forEach { clientBuilder.addInterceptor(it) }
            return clientBuilder
                .callTimeout(TIME_OUT_MINUTES, TimeUnit.MINUTES)
                .readTimeout(TIME_OUT_MINUTES, TimeUnit.MINUTES)
                .writeTimeout(TIME_OUT_MINUTES, TimeUnit.MINUTES)
                .connectTimeout(TIME_OUT_MINUTES, TimeUnit.MINUTES)
                .build()
        }
    }


    @Binds
    @Named(AUTH_INTERCEPTOR_NAME)
    abstract fun provide(authInterceptor: AuthenticationInterceptorRefreshToken): Interceptor

}