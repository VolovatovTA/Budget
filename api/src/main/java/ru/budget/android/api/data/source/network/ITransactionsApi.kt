package ru.budget.android.api.data.source.network

import retrofit2.http.*
import ru.budget.android.api.data.source.network.entity.transactions.TransactionExpenseCreateRequest
import ru.budget.android.api.data.source.network.entity.transactions.TransactionIncomeCreateRequest
import ru.budget.android.api.data.source.network.entity.transactions.TransactionResponse
import ru.budget.android.api.data.source.network.entity.transactions.TransactionTransferCreateRequest

const val pathToTransactions = "wallet/api/v1/transactions"

interface ITransactionsApi {
    @GET(pathToTransactions)
    suspend fun getTransactions(
        @Query("type") type: String? = null,
        @Query("transfer") transferType: String? = null,
        @Query("currency") currency: String? = null,
        @Query("date_from") dateFrom: String? = null,
        @Query("date_to") dateTo: String? = null,
        @Query("expense_ids") expenseIds: String? = null,
        @Query("income_ids") income_ids: String? = null,
        @Query("wallet_ids[]") wallet_ids: List<String>? = null,
    ): TransactionResponse

    @POST("$pathToTransactions/expense")
    suspend fun createTransactionExpense(@Body request: TransactionExpenseCreateRequest): TransactionResponse

    @GET(pathToTransactions)
    suspend fun getExpensesTransactions(
        @Query("type") type: String? = null,
        @Query("currency") currency: String? = null,
        @Query("date_from") dateFrom: String? = null,
        @Query("date_to") dateTo: String? = null,
        @Query("expense_ids[]") expenseIds: List<String>? = null,
    ): TransactionResponse

    @POST("$pathToTransactions/income")
    suspend fun createTransactionIncome(@Body request: TransactionIncomeCreateRequest): TransactionResponse

    @POST("$pathToTransactions/transfer")
    suspend fun createTransactionTransfer(@Body request: TransactionTransferCreateRequest): TransactionResponse

    @DELETE("$pathToTransactions/{id}")
    suspend fun deleteTransaction(@Path("id") id: String)
}