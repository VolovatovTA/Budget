package ru.bysoft.budget.create_update_delete_transactions.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.bysoft.budget.create_update_delete_transactions.data.network.entity.responses.TransactionsCategoryResponse

const val pathToWallet = "wallet/api/v1"
const val pathExpensesName = "expensesName"

interface ITransactionsCategoryApi {
    @GET("$pathToWallet/{$pathExpensesName}")
    suspend fun getCategories(@Path(pathExpensesName) name: String): TransactionsCategoryResponse
}