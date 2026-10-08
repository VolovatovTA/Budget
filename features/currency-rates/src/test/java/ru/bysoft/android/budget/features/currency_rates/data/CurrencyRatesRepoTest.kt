package ru.bysoft.android.budget.features.currency_rates.data

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.budget.android.api.data.mapper.CurrencyRatesDataMapper
import ru.budget.android.api.data.source.network.ICurrencyRatesApi
import ru.budget.android.api.data.source.network.entity.currency_rates.CurrencyRatesResponse
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.currency.getAvailableCurrency
import ru.bysoft.android.budget.features.currency_rates.data.storage.CurrencyRatesDao
import ru.bysoft.android.budget.features.currency_rates.data.storage.entity.CurrencyRateEntity

class CurrencyRatesRepoTest {

    private class FakeApi : ICurrencyRatesApi {
        val requestedBases = mutableListOf<String>()
        override suspend fun getCurrencyRates(base: String): CurrencyRatesResponse {
            requestedBases += base
            val rates = getAvailableCurrency().associate { it.iso4217 to 2.0 }
            return CurrencyRatesResponse(base, rates, "", "success", "", 0, "", 0, "")
        }
    }

    private class FakeDao : CurrencyRatesDao {
        val rows = linkedMapOf<Pair<String, String>, CurrencyRateEntity>()
        override suspend fun getAll() = rows.values.toList()
        override suspend fun get(base: String, target: String) = rows[base to target]
        override suspend fun bases() = rows.keys.map { it.first }.distinct()
        override suspend fun oldestUpdate() = rows.values.minOfOrNull { it.updatedAt }
        override suspend fun upsertAll(rates: List<CurrencyRateEntity>) {
            rates.forEach { rows[it.base to it.target] = it }
        }
    }

    private val day = 24L * 60 * 60 * 1000
    private var clock = 1_000_000L
    private val api = FakeApi()
    private val dao = FakeDao()
    private val repo = CurrencyRatesRepo(api, CurrencyRatesDataMapper(), dao, now = { clock })

    private fun seedAllBases(updatedAt: Long) {
        val all = getAvailableCurrency()
        all.forEach { base -> all.forEach { t -> dao.rows[base.iso4217 to t.iso4217] = CurrencyRateEntity(base.iso4217, t.iso4217, 1.0, updatedAt) } }
    }

    @Test
    fun `empty cache is filled from the api, one request per base currency`() = runTest {
        val data = repo.getCurrencyRates()

        assertEquals(getAvailableCurrency().map { it.iso4217 }, api.requestedBases)
        assertEquals(getAvailableCurrency().toSet(), data.map.keys)
        assertEquals(getAvailableCurrency().size * getAvailableCurrency().size, dao.rows.size)
        assertTrue(dao.rows.values.all { it.updatedAt == clock })
    }

    @Test
    fun `fresh cache is served without touching the api`() = runTest {
        seedAllBases(updatedAt = clock - day / 2)

        val data = repo.getCurrencyRates()

        assertEquals(emptyList<String>(), api.requestedBases)
        assertEquals(1.0, data.map.getValue(BudgetCurrencyEnum.USD).first().rate, 0.0)
    }

    @Test
    fun `cache older than a day is refreshed`() = runTest {
        seedAllBases(updatedAt = clock - day)

        val data = repo.getCurrencyRates()

        assertEquals(getAvailableCurrency().size, api.requestedBases.size)
        assertEquals(2.0, data.map.getValue(BudgetCurrencyEnum.USD).first().rate, 0.0)
    }

    @Test
    fun `cache missing a base currency is refreshed even if young`() = runTest {
        dao.rows["USD" to "EUR"] = CurrencyRateEntity("USD", "EUR", 1.0, clock)

        repo.getCurrencyRates()

        assertEquals(getAvailableCurrency().size, api.requestedBases.size)
    }

    @Test
    fun `single rate comes from the cache when present, otherwise triggers a refresh`() = runTest {
        dao.rows["USD" to "EUR"] = CurrencyRateEntity("USD", "EUR", 0.9, clock - 3 * day)

        val cached = repo.getCurrencyRate(BudgetCurrencyEnum.USD, BudgetCurrencyEnum.EUR)
        assertEquals(0.9, cached.rate, 0.0)
        assertEquals(emptyList<String>(), api.requestedBases)

        val fetched = repo.getCurrencyRate(BudgetCurrencyEnum.EUR, BudgetCurrencyEnum.USD)
        assertEquals(2.0, fetched.rate, 0.0)
        assertEquals(getAvailableCurrency().size, api.requestedBases.size)
    }
}
