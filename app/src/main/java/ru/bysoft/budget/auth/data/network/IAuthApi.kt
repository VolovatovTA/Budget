package ru.bysoft.budget.auth.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import ru.bysoft.budget.auth.data.entity.AuthResponse
import ru.bysoft.budget.auth.data.entity.SignInRequest
import ru.bysoft.budget.auth.data.entity.SignUpRequest


const val postSignInRoute = "api/v1/users/signIn"
const val postSignUpRoute = "api/v1/users/signUp"

interface IAuthApi {
    @POST(postSignInRoute)
    suspend fun signIn(@Body request: SignInRequest): AuthResponse

    @POST(postSignUpRoute)
    suspend fun signUp(@Body request: SignUpRequest): AuthResponse
}