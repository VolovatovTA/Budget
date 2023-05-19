package ru.budget.android.api.data.source.network.entity.me

import com.google.gson.annotations.SerializedName

data class SettingsRequest(
    @SerializedName("currency")
    val currencyRequest: String?,
    @SerializedName("first_day_of_week")
    val firstDayOfWeek: String?,
    @SerializedName("profile_picture_url")
    val pictureUrl: String?
)
