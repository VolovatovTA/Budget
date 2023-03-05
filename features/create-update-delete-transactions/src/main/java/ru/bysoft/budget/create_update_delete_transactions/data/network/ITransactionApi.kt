package ru.bysoft.budget.create_update_delete_transactions.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import ru.bysoft.budget.create_update_delete_transactions.data.network.entity.requests.TransactionExpenseCreateRequest
import ru.bysoft.budget.create_update_delete_transactions.data.network.entity.requests.TransactionIncomeCreateRequest
import ru.bysoft.budget.create_update_delete_transactions.data.network.entity.requests.TransactionTransferCreateRequest
import ru.bysoft.budget.create_update_delete_transactions.data.network.entity.responses.TransactionResponse

const val transactionExpenseCreateRoute = "wallet/api/v1/transactions"
const val pathName = "path"
interface ITransactionApi {

    @POST("$transactionExpenseCreateRoute/expense")
    suspend fun createTransactionExpense(@Body request: TransactionExpenseCreateRequest): TransactionResponse

    @POST("$transactionExpenseCreateRoute/income")
    suspend fun createTransactionIncome(@Body request: TransactionIncomeCreateRequest): TransactionResponse

    @POST("$transactionExpenseCreateRoute/transfer")
    suspend fun createTransactionTransfer(@Body request: TransactionTransferCreateRequest): TransactionResponse

}