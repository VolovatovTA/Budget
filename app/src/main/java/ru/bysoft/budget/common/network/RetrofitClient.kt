package ru.bysoft.budget.common.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val MAIN_BASE_URL_NAME = "mainBaseUrl"
const val MOCK_DELAY_NAME = "mockDelayNAme"

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}