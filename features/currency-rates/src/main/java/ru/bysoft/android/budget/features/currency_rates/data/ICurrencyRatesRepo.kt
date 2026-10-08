package ru.bysoft.android.budget.features.currency_rates.data

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import ru.budget.android.api.data.mapper.CurrencyRatesDataMapper
import ru.budget.android.api.data.source.network.ICurrencyRatesApi
import ru.bysoft.android.budget.common.data_entity.CurrencyRate
import ru.bysoft.android.budget.common.data_entity.CurrencyRateData
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.currency.getAvailableCurrency
import ru.bysoft.android.budget.currency.getCurrency
import ru.bysoft.android.budget.features.currency_rates.data.storage.CurrencyRatesDao
import ru.bysoft.android.budget.features.currency_rates.data.storage.entity.CurrencyRateEntity

interface ICurrencyRatesRepo {
    suspend fun getCurrencyRates(): CurrencyRateData
    suspend fun getCurrencyRate(base: BudgetCurrencyEnum, target: BudgetCurrencyEnum): CurrencyRate
}

/**
 * Offline-first: rates come from Room while every available base currency has rows
 * younger than [maxAge]; otherwise they are fetched for all bases, stored, and returned.
 * Network errors propagate to the caller.
 */
class CurrencyRatesRepo(
    private val api: ICurrencyRatesApi,
    private val mapper: CurrencyRatesDataMapper,
    private val dao: CurrencyRatesDao,
    private val now: () -> Long = System::currentTimeMillis,
    private val maxAge: Long = DAY_MILLIS,
) : ICurrencyRatesRepo {

    override suspend fun getCurrencyRates(): CurrencyRateData {
        val bases = getAvailableCurrency()
        if (bases.isEmpty()) return CurrencyRateData(emptyMap())
        return if (isFresh(bases)) fromStorage() else refresh(bases)
    }

    override suspend fun getCurrencyRate(
        base: BudgetCurrencyEnum,
        target: BudgetCurrencyEnum,
    ): CurrencyRate {
        dao.get(base.iso4217, target.iso4217)?.let { return CurrencyRate(target, it.rate) }
        return refresh(getAvailableCurrency()).map[base]
            ?.firstOrNull { it.currency == target }
            ?: CurrencyRate(target, Double.NaN)
    }

    private suspend fun isFresh(bases: List<BudgetCurrencyEnum>): Boolean {
        val oldest = dao.oldestUpdate() ?: return false
        if (now() - oldest >= maxAge) return false
        val stored = dao.bases().toSet()
        return bases.all { it.iso4217 in stored }
    }

    private suspend fun fromStorage(): CurrencyRateData = CurrencyRateData(
        dao.getAll()
            .groupBy { getCurrency(it.base) }
            .mapValues { (_, rows) -> rows.map { CurrencyRate(getCurrency(it.target), it.rate) } }
    )

    private suspend fun refresh(bases: List<BudgetCurrencyEnum>): CurrencyRateData {
        val responses = coroutineScope {
            bases.map { async { api.getCurrencyRates(it.iso4217) } }.awaitAll()
        }
        val data = mapper.getCurrencyRates(responses)
        val syncedAt = now()
        dao.upsertAll(data.map.flatMap { (base, rates) ->
            rates.map { CurrencyRateEntity(base.iso4217, it.currency.iso4217, it.rate, syncedAt) }
        })
        return data
    }

    private companion object {
        const val DAY_MILLIS = 24L * 60 * 60 * 1000
    }
}
