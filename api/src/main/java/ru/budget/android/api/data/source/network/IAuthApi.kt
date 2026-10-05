package ru.budget.android.api.data.source.network

import retrofit2.http.Body
import retrofit2.http.POST
import ru.budget.android.api.data.source.network.entity.auth.SignInGoogleRequest
import ru.budget.android.api.data.source.network.entity.auth.SignInRequest
import ru.budget.android.api.data.source.network.entity.auth.SignUpRequest
import ru.bysoft.android.budget.common.token.entity.AuthSuccessResponse

const val postSignInRoute = "wallet/api/v1/auth/signIn"
const val postSignUpRoute = "wallet/api/v1/auth/signUp"
const val postSignInGoogleRoute = "wallet/api/v1/auth/google-signIn"

interface IAuthApi {
    @POST(postSignInRoute)
    suspend fun signIn(@Body request: SignInRequest): AuthSuccessResponse

    @POST(postSignUpRoute)
    suspend fun signUp(@Body request: SignUpRequest): AuthSuccessResponse

    @POST(postSignInGoogleRoute)
    suspend fun signInByGoogle(@Body request: SignInGoogleRequest): AuthSuccessResponse

}