package ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import ru.bysoft.android.budget.common.util.getStringFromAsset
import ru.bysoft.android.budget.common.util.restore
import ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions.network.entity.*
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class TransactionApiMock @Inject constructor(
    @ApplicationContext private val context: Context
) : ITransactionsApi {
    override suspend fun getTransactions(
        type: String?,
        transferType: String?,
        currency: String?,
        dateFrom: String?,
        dateTo: String?,
        expenseIds: String?,
        income_ids: String?,
        wallet_ids: List<String>?
    ): ListTransactionsResponse {
        delay(500)
        val response = context.getStringFromAsset("transactions/transactions.json")
            .restore<ListTransactionsResponse>()
        return response.copy(
            data = response.data.subList(0, Random.nextInt(0, response.data.size))
                .map { it.copy(id = UUID.randomUUID().toString()) }
        )
    }

    var switcher = false
    override suspend fun deleteTransaction(id: String): String {
        delay(1000)
        switcher = !switcher
        return if (switcher) "[]" else throw Throwable("Error")
    }
}