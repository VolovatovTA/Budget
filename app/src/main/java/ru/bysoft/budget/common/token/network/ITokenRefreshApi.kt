package ru.bysoft.budget.common.token.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import ru.bysoft.budget.auth.data.network.entity.AuthSuccessResponse
import ru.bysoft.budget.common.token.entity.TokenRefreshRequest

interface ITokenRefreshApi {
    @POST("users/api/v1/refresh")
    fun refresh(@Body refreshToken: TokenRefreshRequest): Call<AuthSuccessResponse>
}