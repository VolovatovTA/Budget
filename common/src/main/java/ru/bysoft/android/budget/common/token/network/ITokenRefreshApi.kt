package ru.bysoft.android.budget.common.token.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import ru.bysoft.android.budget.common.token.entity.AuthSuccessResponse
import ru.bysoft.android.budget.common.token.entity.TokenRefreshRequest

interface ITokenRefreshApi {
    @POST("auth/api/v1/refresh")
    fun refresh(@Body refreshToken: TokenRefreshRequest): Call<AuthSuccessResponse>
}