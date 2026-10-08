package ru.bysoft.android.budget.common.token.network

import retrofit2.http.Body
import retrofit2.http.POST
import ru.bysoft.android.budget.common.token.entity.AuthSuccessResponse
import ru.bysoft.android.budget.common.token.entity.TokenRefreshRequest

interface ITokenRefreshApi {
    @POST("wallet/api/v1/auth/refresh")
    suspend fun refresh(@Body refreshToken: TokenRefreshRequest): AuthSuccessResponse
}