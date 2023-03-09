package ru.bysoft.android.budget.features.bottom_navigation.statistic.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.bysoft.android.budget.features.bottom_navigation.statistic.data.network.entity.ListTransactionsResponse
import ru.bysoft.android.budget.features.bottom_navigation.statistic.data.network.entity.StatisticExpenseResponse

const val pathToExpenses = "wallet/api/v1/expenses"
const val pathToTransactions = "wallet/api/v1/transactions"
interface IStatisticApi {
    @GET(pathToExpenses)
    suspend fun getExpenses(): StatisticExpenseResponse


    @GET(pathToTransactions)
    suspend fun getExpensesTransactions(
        @Query("type") type: String? = null,
        @Query("currency") currency: String? = null,
        @Query("date_from") dateFrom: String? = null,
        @Query("date_to") dateTo: String? = null,
        @Query("expense_ids[]") expenseIds: List<String>? = null,
    ): ListTransactionsResponse
}