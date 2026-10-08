package ru.budget.android.api.data.source.mock

import android.content.Context
import ru.budget.android.api.data.source.network.ICurrencyRatesApi
import ru.budget.android.api.data.source.network.entity.currency_rates.CurrencyRatesResponse
import ru.bysoft.android.budget.common.util.getStringFromAsset
import ru.bysoft.android.budget.common.util.restore

class CurrencyRateApiMock(
    private val context: Context
): ICurrencyRatesApi {
    override suspend fun getCurrencyRates(base: String): CurrencyRatesResponse {
        // в файле курсы относительно USD, для другой базовой валюты пересчитываем
        val usdRates = context.getStringFromAsset("currency_rates/mock.json").restore<CurrencyRatesResponse>()
        val baseRate = usdRates.conversionRates[base] ?: return usdRates
        return usdRates.copy(
            baseCode = base,
            conversionRates = usdRates.conversionRates.mapValues { (_, rate) -> rate?.div(baseRate) }
        )
    }
}