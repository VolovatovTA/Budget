package ru.bysoft.android.budget.features.currency_rates.data

import kotlinx.coroutines.*
import ru.budget.android.api.data.mapper.CurrencyRatesDataMapper
import ru.budget.android.api.data.source.network.ICurrencyRatesApi
import ru.bysoft.android.budget.common.data_entity.CurrencyRate
import ru.bysoft.android.budget.common.data_entity.CurrencyRateData
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.currency.getAvailableCurrency
import ru.bysoft.android.budget.currency.getCurrency
import ru.bysoft.android.budget.features.currency_rates.data.storage.ICurrencyRatesLocalStorage
import ru.bysoft.android.budget.features.currency_rates.data.storage.entity.CurrencyRatesEntity
import javax.inject.Inject

interface ICurrencyRatesRepo {
    suspend fun getCurrencyRates(): CurrencyRateData
    suspend fun getCurrencyRate(base: BudgetCurrencyEnum, target: BudgetCurrencyEnum): CurrencyRate
}

class CurrencyRatesRepo @Inject constructor(
    private val api: ICurrencyRatesApi,
    private val mapper: CurrencyRatesDataMapper,
    private val currencyRatesStorage: ICurrencyRatesLocalStorage,
    private val errorLogger: IErrorLogger
) : ICurrencyRatesRepo {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val handler =
        CoroutineExceptionHandler { _, throwable -> errorLogger.logError(throwable) }

    override suspend fun getCurrencyRates(): CurrencyRateData {

        val availableCurrency = getAvailableCurrency()
        val firstCurrency =
            availableCurrency.firstOrNull() ?: return CurrencyRateData(emptyMap())
        val extractedData = withContext(scope.coroutineContext + handler) {
            currencyRatesStorage.getCurrencyRateByIso(firstCurrency.iso4217)
        }
        val extractedMillis = extractedData?.list?.split('|')?.get(1)?.toLongOrNull() ?: 0L
        val currentMillis = System.currentTimeMillis()
        val difference = currentMillis - extractedMillis
        val millisecondsInDay = 1000 * 60 * 60 * 24

        return if (extractedData != null && difference < millisecondsInDay) {
            getCurrecyRatesFromStorage()
        } else {
            getCurrencyRatesFromApiAndSaveItInStorage(availableCurrency)
        }

    }

    private suspend fun getCurrencyRatesFromApiAndSaveItInStorage(availableCurrency: List<BudgetCurrencyEnum>): CurrencyRateData {
        val currencyRates = availableCurrency.map { it.iso4217 }.map {
            scope.async(handler) { api.getCurrencyRates(it) }
        }.awaitAll()
        val data = mapper.getCurrencyRates(currencyRates)
        data.map.forEach { (budgetCurrency, currencyRates) ->
            scope.launch(handler) {
                if (currencyRatesStorage.getCurrencyRateByIso(budgetCurrency.iso4217) != null) {
                    currencyRatesStorage.updateCurrencyRate(
                        getCurrencyRateEntities(
                            budgetCurrency,
                            currencyRates
                        )
                    )
                } else {
                    currencyRatesStorage.insertCurrencyRate(
                        getCurrencyRateEntities(
                            budgetCurrency,
                            currencyRates
                        )
                    )
                }
            }
        }
        return data
    }

    private suspend fun getCurrecyRatesFromStorage(): CurrencyRateData {
        val data = withContext(scope.coroutineContext + handler) {
            mapToListCurrencyRatesData(currencyRatesStorage.getAllCurrencyRates())
        }
        return CurrencyRateData(data)
    }

    private fun mapToListCurrencyRatesData(list: List<CurrencyRatesEntity>): Map<BudgetCurrencyEnum, List<CurrencyRate>> {
        val result = list
            .map { it.iso4217 to it.list.split('|')[0] }
            .associate {
                getCurrency(it.first) to it.second.mapToCurrencyRateData()
            }
        return result
    }


    private fun String.mapToCurrencyRateData(): List<CurrencyRate> = this
        .split('|')[0]
        .split(";")
        .map { curWithDouble ->
            val data = curWithDouble.split(",")
            CurrencyRate(getCurrency(data[0]), data[1].toDouble())
        }

    override suspend fun getCurrencyRate(
        base: BudgetCurrencyEnum,
        target: BudgetCurrencyEnum
    ): CurrencyRate {
        withContext(scope.coroutineContext) {
            currencyRatesStorage.getCurrencyRateByIso(base.iso4217)
        }?.let {
            return it.list.mapToCurrencyRateData()
                .firstOrNull { currencyRate -> currencyRate.currency == target }
                ?: CurrencyRate(target, Double.NaN)
        }

        val data = getCurrencyRatesFromApiAndSaveItInStorage(getAvailableCurrency())
        return data.map[base]?.firstOrNull { it.currency == target }
            ?: CurrencyRate(target, Double.NaN)
    }
}

fun getCurrencyRateEntities(
    base: BudgetCurrencyEnum,
    rates: List<CurrencyRate>
): CurrencyRatesEntity {
    return CurrencyRatesEntity(
        iso4217 = base.iso4217,
        list = rates.joinToString(separator = ";") { "${it.currency.iso4217},${it.rate}" }
            .plus("|${System.currentTimeMillis()}")
    )
}