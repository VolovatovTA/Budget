package ru.budget.android.api.data.source.network

import retrofit2.http.GET
import ru.budget.android.api.data.source.network.entity.me.MeResponse

const val pathSettings = "auth/api/v1/me"

interface IMeApi {
    @GET(pathSettings)
    suspend fun getMeInfo(): MeResponse
}