package ru.budget.android.api.data.source.network.entity.me

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MeResponse(
    @SerialName("email")
    val email: String,
    @SerialName("name")
    val name: String,
    @SerialName("settings")
    val settingsResponse: SettingsResponse?,
    @SerialName("uuid")
    val userId: String
)

@Serializable
data class SettingsResponse(
    @SerialName("currency")
    val currencyResponse: String?,
    @SerialName("first_day_of_week")
    val firstDayOfWeek: String?,
    @SerialName("profile_picture_url")
    val pictureUrl: String?
)