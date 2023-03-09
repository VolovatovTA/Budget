package ru.bysoft.android.budget.features.bottom_navigation.home.data.me.network.entity

import com.google.gson.annotations.SerializedName

data class MeResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("settings")
    val settingsResponse: SettingsResponse,
    @SerializedName("uuid")
    val userId: String
)

data class SettingsResponse(
    @SerializedName("currency")
    val currencyResponse: String
)