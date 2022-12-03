package ru.bysoft.budget.home.data.me.network

import retrofit2.http.GET
import ru.bysoft.budget.home.data.me.network.entity.MeResponse

const val pathSettings = "users/api/v1/me"

interface IHomeMeApi {
    @GET(pathSettings)
    suspend fun getMeInfo(): MeResponse
}