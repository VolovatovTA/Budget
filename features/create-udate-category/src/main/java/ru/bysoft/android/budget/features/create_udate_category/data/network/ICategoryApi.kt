package ru.bysoft.android.budget.features.create_udate_category.data.network

import retrofit2.Call
import retrofit2.http.*
import ru.bysoft.android.budget.features.create_udate_category.data.network.entity.CategoryRequest
import ru.bysoft.android.budget.features.create_udate_category.data.network.entity.CategoryResponse

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