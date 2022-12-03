package ru.bysoft.budget.home.data.me.network.entity

import com.google.gson.annotations.SerializedName

data class MeResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("settings")
    val settings: Settings,
    @SerializedName("userId")
    val userId: String
)

data class Settings(
    @SerializedName("currency")
    val currency: String
)