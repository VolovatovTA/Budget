package ru.budget.android.api.data.source.network

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import ru.budget.android.api.data.source.network.entity.me.MeResponse
import ru.budget.android.api.data.source.network.entity.me.SettingsRequest

const val pathMe = "wallet/api/v1/auth/me"
const val pathSettings = "wallet/api/v1/auth/settings"
const val pathDelete = "wallet/api/v1/auth/delete"

interface IMeApi {
    @GET(pathMe)
    suspend fun getMeInfo(): MeResponse

    @PUT(pathSettings)
    suspend fun setMeInfo(@Body request: SettingsRequest)

    @DELETE(pathDelete)
    suspend fun deleteAccount()
}