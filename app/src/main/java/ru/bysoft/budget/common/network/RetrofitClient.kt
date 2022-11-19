package ru.bysoft.budget.common.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val MAIN_BASE_URL_NAME = "mainBaseUrl"
const val INTERCEPTORS_LIST_NAME = "listInterceptors"
const val MOCK_DELAY_NAME = "mockDelayNAme"

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getApi(baseUrl: String, interceptors: Set<Interceptor>): Retrofit {

        val clientBuilder = OkHttpClient.Builder()
        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}