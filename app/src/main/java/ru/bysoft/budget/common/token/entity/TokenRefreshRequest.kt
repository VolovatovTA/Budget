package ru.bysoft.budget.common.token.entity

import com.google.gson.annotations.SerializedName


data class TokenRefreshRequest(
    @SerializedName("refresh")
    val refreshToken: String
)
