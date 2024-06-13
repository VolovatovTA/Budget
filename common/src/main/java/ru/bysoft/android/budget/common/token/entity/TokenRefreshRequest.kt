package ru.bysoft.android.budget.common.token.entity

import com.google.gson.annotations.SerializedName

data class TokenRefreshRequest(
    @SerializedName("refresh")
    val refreshToken: String
)

sealed interface AuthResponse

data class AuthSuccessResponse(
    @SerializedName("access")
    val accessToken: String? = null,
    @SerializedName("refresh")
    val refreshToken: String? = null,
) : AuthResponse

data class SignErrorResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("error")
    val error: String? = null,
    @SerializedName("errors")
    val errors: Errors? = null
) : AuthResponse {
    data class Errors(
        @SerializedName("email")
        val email: List<String>?,
        @SerializedName("password")
        val password: List<String>?,
        @SerializedName("name")
        val name: List<String>?
    )
}
