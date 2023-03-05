package ru.bysoft.budget.features.bottom_navigation.home.data.transactions.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.bysoft.budget.features.bottom_navigation.home.data.transactions.network.entity.ListTransactionsResponse

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
    ): ListTransactionsResponse
}