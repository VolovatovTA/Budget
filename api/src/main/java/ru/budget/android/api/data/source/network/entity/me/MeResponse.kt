package ru.budget.android.api.data.source.network.entity.me

import com.google.gson.annotations.SerializedName

data class MeResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("settings")
    val settingsResponse: SettingsResponse,
    @SerializedName("uuid")
    val userId: String,
    @SerializedName("pictureUrl")
    val pictureUrl: String?
)

data class SettingsResponse(
    @SerializedName("currency")
    val currencyResponse: String
)