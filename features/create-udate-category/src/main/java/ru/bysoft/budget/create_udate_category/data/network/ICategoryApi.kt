package ru.bysoft.budget.create_udate_category.data.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.bysoft.budget.create_udate_category.data.network.entity.CategoryDeleteResponse
import ru.bysoft.budget.create_udate_category.data.network.entity.CategoryRequest
import ru.bysoft.budget.create_udate_category.data.network.entity.CategoryResponse

const val pathToCategory = "wallet/api/v1/"
const val namePathExpenseIncome = "namePathExpenseIncome"
const val nameId = "id"

interface ICategoryApi {
    @POST("$pathToCategory{$namePathExpenseIncome}")
    suspend fun createCategory(
        @Body request: CategoryRequest,
        @Path(namePathExpenseIncome) path: String
    ): CategoryResponse


    @PUT("$pathToCategory{$namePathExpenseIncome}/{$nameId}")
    suspend fun updateCategory(
        @Body request: CategoryRequest,
        @Path(namePathExpenseIncome) path: String,
        @Path(nameId) id: String
    ): CategoryResponse

    @GET("$pathToCategory{$namePathExpenseIncome}/{$nameId}")
    suspend fun getCategory(
        @Path(nameId) id: String,
        @Path(namePathExpenseIncome) path: String
    ): CategoryResponse

    @DELETE("$pathToCategory{$namePathExpenseIncome}/{$nameId}")
    fun delete(
        @Path(nameId) id: String,
        @Path(namePathExpenseIncome) path: String
    ): Call<Unit>
}