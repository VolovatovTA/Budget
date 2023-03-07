package ru.bysoft.budget.create_update_delete_transactions.data.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import ru.bysoft.budget.common.network.MOCK_DELAY_NAME
import ru.bysoft.budget.common.util.getStringFromAsset
import ru.bysoft.budget.common.util.restore
import ru.bysoft.budget.create_update_delete_transactions.data.network.entity.requests.TransactionExpenseCreateRequest
import ru.bysoft.budget.create_update_delete_transactions.data.network.entity.requests.TransactionIncomeCreateRequest
import ru.bysoft.budget.create_update_delete_transactions.data.network.entity.requests.TransactionTransferCreateRequest
import ru.bysoft.budget.create_update_delete_transactions.data.network.entity.responses.TransactionResponse
import javax.inject.Inject
import javax.inject.Named

class TransactionApiMock @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named(MOCK_DELAY_NAME) private val delayMock: Long
) : ITransactionApi {

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