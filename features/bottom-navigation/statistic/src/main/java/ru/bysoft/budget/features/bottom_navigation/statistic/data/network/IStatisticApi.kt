package ru.bysoft.budget.features.bottom_navigation.statistic.data.network

import retrofit2.http.GET
import ru.bysoft.budget.features.bottom_navigation.statistic.data.network.entity.StatisticExpenseResponse

const val pathToExpenses = "wallet/api/v1/expenses"
interface IStatisticApi {
    @GET(pathToExpenses)
    suspend fun getExpenses(): StatisticExpenseResponse
}