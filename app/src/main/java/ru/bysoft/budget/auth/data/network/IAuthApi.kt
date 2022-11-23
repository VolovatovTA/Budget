package ru.bysoft.budget.auth.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import ru.bysoft.budget.auth.data.network.entity.*

const val postSignInRoute = "users/api/v1/signIn"
const val postSignUpRoute = "users/api/v1/signUp"

interface IAuthApi {
    @POST(postSignInRoute)
    suspend fun signIn(@Body request: SignInRequest): AuthSuccessResponse

    @POST(postSignUpRoute)
    suspend fun signUp(@Body request: SignUpRequest): AuthSuccessResponse

}