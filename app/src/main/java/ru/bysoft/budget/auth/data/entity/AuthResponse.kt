package ru.bysoft.budget.auth.data.entity
import com.google.gson.annotations.SerializedName


data class SignUpRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String
)

data class SignInRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)

data class AuthResponse(
    @SerializedName("access")
    val accessToken: String,
    @SerializedName("refresh")
    val refreshToken: String
)