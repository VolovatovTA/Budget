package ru.bysoft.android.budget.auth.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import ru.bysoft.android.budget.auth.data.network.entity.SignInRequest
import ru.bysoft.android.budget.auth.data.network.entity.SignUpRequest
import ru.bysoft.android.budget.auth.data.network.entity.*
import ru.bysoft.android.budget.common.token.entity.AuthSuccessResponse

const val postSignInRoute = "users/api/v1/signIn"
const val postSignUpRoute = "users/api/v1/signUp"

interface IAuthApi {
    @POST(postSignInRoute)
    suspend fun signIn(@Body request: SignInRequest): AuthSuccessResponse

    @POST(postSignUpRoute)
    suspend fun signUp(@Body request: SignUpRequest): AuthSuccessResponse

}