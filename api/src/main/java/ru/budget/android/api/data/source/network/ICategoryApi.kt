package ru.budget.android.api.data.source.network

import retrofit2.Call
import retrofit2.http.*
import ru.budget.android.api.data.source.network.entity.category.*

const val pathToWallet = "api/v1"
const val pathExpensesName = "expensesName"
const val pathToCategory = "api/v1/"
const val namePathExpenseIncome = "namePathExpenseIncome"
const val nameId = "id"

interface ICategoryApi {

    @GET("$pathToWallet/{$pathExpensesName}")
    suspend fun getCategories(@Path(pathExpensesName) name: String): CategoryResponse


    @POST("$pathToCategory{$namePathExpenseIncome}")
    suspend fun createCategory(
        @Body request: CategoryRequest,
        @Path(namePathExpenseIncome) path: String
    ): CategoryItemResponse


    @PUT("$pathToCategory{$namePathExpenseIncome}/{$nameId}")
    suspend fun updateCategory(
        @Body request: CategoryRequest,
        @Path(namePathExpenseIncome) path: String,
        @Path(nameId) id: String
    ): CategoryItemResponse

    @GET("$pathToCategory{$namePathExpenseIncome}/{$nameId}")
    suspend fun getCategory(
        @Path(nameId) id: String,
        @Path(namePathExpenseIncome) path: String
    ): CategoryItemResponse

    @DELETE("$pathToCategory{$namePathExpenseIncome}/{$nameId}")
    suspend fun delete(
        @Path(nameId) id: String,
        @Path(namePathExpenseIncome) path: String
    )
}