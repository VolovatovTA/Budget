package ru.bysoft.budget.auth.network

import retrofit2.http.POST

interface IAuthApi {
    @POST("users/api/v1/auth/signUp")
    fun sendUserCredential(phone: String)
}