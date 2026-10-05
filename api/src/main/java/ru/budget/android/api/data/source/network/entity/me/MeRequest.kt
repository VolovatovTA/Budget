package ru.budget.android.api.data.source.network.entity.me

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SettingsRequest(
    @SerialName("currency")
    val currencyRequest: String?,
    @SerialName("first_day_of_week")
    val firstDayOfWeek: String?,
    @SerialName("profile_picture_url")
    val pictureUrl: String?
)
