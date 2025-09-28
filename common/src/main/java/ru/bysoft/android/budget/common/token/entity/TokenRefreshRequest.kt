package ru.bysoft.android.budget.common.token.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenRefreshRequest(
    @SerialName("refresh")
    val refreshToken: String
)

@Serializable
sealed interface AuthResponse

@Serializable
data class AuthSuccessResponse(
    @SerialName("access")
    val accessToken: String? = null,
    @SerialName("refresh")
    val refreshToken: String? = null,
) : AuthResponse

@Serializable
data class SignErrorResponse(
    @SerialName("message")
    val message: String? = null,
    @SerialName("error")
    val error: String? = null,
    @SerialName("errors")
    val errors: Errors? = null
) : AuthResponse {
    @Serializable
    data class Errors(
        @SerialName("email")
        val email: List<String>?,
        @SerialName("password")
        val password: List<String>?,
        @SerialName("name")
        val name: List<String>?
    )
}
