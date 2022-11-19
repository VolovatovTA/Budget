package ru.bysoft.budget.auth.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import ru.bysoft.budget.auth.data.network.entity.AuthResponse
import ru.bysoft.budget.auth.data.network.entity.SignInRequest
import ru.bysoft.budget.auth.data.network.entity.SignUpRequest

const val postSignInRoute = "users/api/v1/signIn"
const val postSignUpRoute = "users/api/v1/signUp"

interface IAuthApi {
    @POST(postSignInRoute)
    suspend fun signIn(@Body request: SignInRequest): AuthResponse

    @POST(postSignUpRoute)
    suspend fun signUp(@Body request: SignUpRequest): AuthResponse
}