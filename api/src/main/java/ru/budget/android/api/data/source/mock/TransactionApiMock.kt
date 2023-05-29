package ru.budget.android.api.data.source.mock

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import ru.budget.android.api.data.source.network.ITransactionsApi
import ru.budget.android.api.data.source.network.entity.transactions.TransactionExpenseCreateRequest
import ru.budget.android.api.data.source.network.entity.transactions.TransactionIncomeCreateRequest
import ru.budget.android.api.data.source.network.entity.transactions.TransactionResponse
import ru.budget.android.api.data.source.network.entity.transactions.TransactionTransferCreateRequest
import ru.bysoft.android.budget.common.network.MOCK_DELAY_NAME
import ru.bysoft.android.budget.common.util.getStringFromAsset
import ru.bysoft.android.budget.common.util.restore
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.random.Random

class TransactionApiMock @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named(MOCK_DELAY_NAME) private val delayMock: Long
) : ITransactionsApi {
    override suspend fun getExpensesTransactions(
        type: String?,
        currency: String?,
        dateFrom: String?,
        dateTo: String?,
        expenseIds: List<String>?
    ): TransactionResponse {
        return context.getStringFromAsset("").restore()
    }
    override suspend fun getTransactions(
        type: String?,
        transferType: String?,
        currency: String?,
        dateFrom: String?,
        dateTo: String?,
        expenseIds: String?,
        income_ids: String?,
        wallet_ids: List<String>?
    ): TransactionResponse {
        delay(delayMock)
        val response = context.getStringFromAsset("transactions/transactions.json")
            .restore<TransactionResponse>()
        return response.copy(
            data = response.data?.subList(0, Random.nextInt(0, response.data.size))
                ?.map { it?.copy(id = UUID.randomUUID().toString()) }
        )
    }

    override suspend fun deleteTransaction(id: String): Unit {
        delay(delayMock)
    }

    override suspend fun createTransactionExpense(request: TransactionExpenseCreateRequest): TransactionResponse {
        delay(delayMock)
        return context.getStringFromAsset("").restore()
    }

    override suspend fun createTransactionIncome(request: TransactionIncomeCreateRequest): TransactionResponse {
        delay(delayMock)
        return context.getStringFromAsset("").restore()
    }

    override suspend fun createTransactionTransfer(request: TransactionTransferCreateRequest): TransactionResponse {
        delay(delayMock)
        return context.getStringFromAsset("").restore()
    }
}