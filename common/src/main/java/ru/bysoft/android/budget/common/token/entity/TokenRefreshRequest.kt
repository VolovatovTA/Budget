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
): AuthResponse

data class SignUpErrorResponse(
    @SerializedName("slug")
    val slug: String? = null
): AuthResponse

data class SignInErrorResponse(
    @SerializedName("slug")
    val slug: String? = null
): AuthResponse
