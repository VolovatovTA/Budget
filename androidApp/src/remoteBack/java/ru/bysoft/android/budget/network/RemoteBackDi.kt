package ru.bysoft.android.budget.network

import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.bysoft.android.budget.common.network.LOG_INTERCEPTORS_NAME
import ru.bysoft.android.budget.common.network.authentificator.AuthenticationInterceptorRefreshToken
import ru.bysoft.android.budget.common.token.network.ITokenRefreshApi
import java.util.concurrent.TimeUnit

const val MAIN_BASE_URL_NAME = "mainBaseUrl"
const val WALLET_BASE_URL_NAME = "walletBaseUrl"
const val AUTH_CLIENT_NAME = "clientWithAuth"
const val NO_AUTH_CLIENT_NAME = "clientWithoutAuth"

private const val TIME_OUT_MINUTES = 2L

val RemoteBackDi = module {
    single(named(MAIN_BASE_URL_NAME)) { "https://imbsoft.tech" }
    single(named(WALLET_BASE_URL_NAME)) { "https://imbsoft.tech" }

    singleOf(::AuthenticationInterceptorRefreshToken)

    single(named(NO_AUTH_CLIENT_NAME)) {
        okHttpClient(get(named(LOG_INTERCEPTORS_NAME)))
    }
    single(named(AUTH_CLIENT_NAME)) {
        okHttpClient(get<List<Interceptor>>(named(LOG_INTERCEPTORS_NAME)) + get<AuthenticationInterceptorRefreshToken>())
    }

    single<ITokenRefreshApi> {
        retrofit(get(named(MAIN_BASE_URL_NAME)), get(named(NO_AUTH_CLIENT_NAME)))
    }
}

private fun okHttpClient(interceptors: List<Interceptor>): OkHttpClient =
    OkHttpClient.Builder()
        .apply { interceptors.forEach { addInterceptor(it) } }
        .callTimeout(TIME_OUT_MINUTES, TimeUnit.MINUTES)
        .readTimeout(TIME_OUT_MINUTES, TimeUnit.MINUTES)
        .writeTimeout(TIME_OUT_MINUTES, TimeUnit.MINUTES)
        .connectTimeout(TIME_OUT_MINUTES, TimeUnit.MINUTES)
        .build()

private val json = Json { ignoreUnknownKeys = true }

/** Retrofit с kotlinx.serialization: модели ответов размечены @SerialName, Gson их не понимает */
inline fun <reified T> retrofit(baseUrl: String, client: OkHttpClient): T =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(jsonConverterFactory)
        .build()
        .create(T::class.java)

val jsonConverterFactory = json.asConverterFactory("application/json".toMediaType())
