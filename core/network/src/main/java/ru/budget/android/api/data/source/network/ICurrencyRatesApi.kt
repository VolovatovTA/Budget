package ru.budget.android.api.data.source.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.budget.android.api.data.source.network.entity.currency_rates.CurrencyRatesResponse

const val keyBase = "base"
interface ICurrencyRatesApi {
    @GET("{$keyBase}")
    suspend fun getCurrencyRates(@Path(keyBase) base: String): CurrencyRatesResponse
}