package ru.budget.android.api.data.source.mock

import android.content.Context
import kotlinx.coroutines.delay
import ru.budget.android.api.data.source.network.ITransactionsApi
import ru.budget.android.api.data.source.network.entity.transactions.TransactionExpenseCreateRequest
import ru.budget.android.api.data.source.network.entity.transactions.TransactionIncomeCreateRequest
import ru.budget.android.api.data.source.network.entity.transactions.TransactionResponse
import ru.budget.android.api.data.source.network.entity.transactions.TransactionTransferCreateRequest
import ru.budget.android.api.data.source.network.pathToTransactions
import ru.bysoft.android.budget.common.util.getStringFromAsset
import ru.bysoft.android.budget.common.util.pointJson
import ru.bysoft.android.budget.common.util.restore

class TransactionApiMock (
    private val context: Context,
    private val delayMock: Long
) : ITransactionsApi {
    override suspend fun getExpensesTransactions(
        type: String?,
        currency: String?,
        dateFrom: String?,
        dateTo: String?,
        expenseIds: List<String>?
    ): TransactionResponse {
        val response = allTransactions()
        return response.copy(
            data = response.data?.filter { transaction ->
                transaction?.listTransactionExpenseResponse.orEmpty().any { it.id in expenseIds.orEmpty() }
            }
        )
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
        return allTransactions()
    }

    override suspend fun deleteTransaction(id: String): Unit {
        delay(delayMock)
    }

    override suspend fun createTransactionExpense(request: TransactionExpenseCreateRequest): TransactionResponse {
        delay(delayMock)
        return allTransactions()
    }

    override suspend fun createTransactionIncome(request: TransactionIncomeCreateRequest): TransactionResponse {
        delay(delayMock)
        return allTransactions()
    }

    override suspend fun createTransactionTransfer(request: TransactionTransferCreateRequest): TransactionResponse {
        delay(delayMock)
        return allTransactions()
    }

    private fun allTransactions(): TransactionResponse =
        context.getStringFromAsset(pathToTransactions + pointJson).restore()
}