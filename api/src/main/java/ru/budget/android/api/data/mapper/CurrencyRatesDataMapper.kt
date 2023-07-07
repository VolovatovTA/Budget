package ru.budget.android.api.data.mapper

import ru.budget.android.api.data.source.network.entity.currency_rates.CurrencyRatesResponse
import ru.bysoft.android.budget.common.data_entity.CurrencyRate
import ru.bysoft.android.budget.common.data_entity.CurrencyRateData
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.currency.getAvailableCurrency
import ru.bysoft.android.budget.common.util.onNull
import javax.inject.Inject

class CurrencyRatesDataMapper @Inject constructor() {
    fun getCurrencyRates(responses: List<CurrencyRatesResponse>): CurrencyRateData {
        return CurrencyRateData(
            responses
                .asSequence()
                .map { ratesResponse ->
                    val availableCurrency = getAvailableCurrency()
                    val budgetCurrency =
                        availableCurrency.firstOrNull { it.iso4217 == ratesResponse.baseCode }
                            ?: BudgetCurrencyEnum.UNKNOWN
                    budgetCurrency to ratesResponse.conversionRates.map { (code, rate) ->
                        val currency = availableCurrency.firstOrNull { it.iso4217 == code }
                            ?: BudgetCurrencyEnum.UNKNOWN
                        CurrencyRate(currency, rate.onNull { Double.NaN })
                    }
                        .filter { it.currency != BudgetCurrencyEnum.UNKNOWN }

                }
                .filter { it.first != BudgetCurrencyEnum.UNKNOWN }
                .associate { it.first to it.second }
        )
    }
}