package ru.budget.android.api.data.source.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import ru.budget.android.api.data.source.network.entity.me.MeResponse
import ru.budget.android.api.data.source.network.entity.me.SettingsRequest

const val pathMe = "auth/api/v1/me"
const val pathSettings = "auth/api/v1/settings"

interface IMeApi {
    @GET(pathMe)
    suspend fun getMeInfo(): MeResponse

    @PUT(pathSettings)
    suspend fun setMeInfo(@Body request: SettingsRequest)
}