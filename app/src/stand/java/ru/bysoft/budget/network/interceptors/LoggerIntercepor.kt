package ru.bysoft.budget.network.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import ru.bysoft.budget.common.util.toJson
import javax.inject.Inject
import javax.inject.Singleton

const val TAG = "networkLog"

interface IStandInterceptor : Interceptor

@Singleton
class LoggerIntercepor @Inject constructor(): IStandInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        Log.d(TAG, "$originalRequest ")
        Log.d(TAG, originalRequest.headers().toJson())
        val response = chain.proceed(originalRequest)
        Log.d(TAG, "$response ")
        return response
    }
}

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("appid", "hello")
                .addHeader("deviceplatform", "android")
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0")
                .build()
        )
    }
}