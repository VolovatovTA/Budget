package ru.bysoft.budget.create_update_delete_transactions.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import ru.bysoft.budget.create_update_delete_transactions.data.network.entity.requests.TransactionCreateRequest
import ru.bysoft.budget.create_update_delete_transactions.data.network.entity.responses.TransactionResponse

const val transactionExpenseCreateRoute = "wallet/api/v1/transactions/expense"
interface ITransactionApi {

    @POST(transactionExpenseCreateRoute)
    suspend fun createTransaction(@Body request: TransactionCreateRequest): TransactionResponse

}