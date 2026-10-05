package ru.bysoft.android.budget.features.bottom_navigation.home.data.me.mapper

import ru.bysoft.android.budget.common.me_info.entity.MeData
import ru.bysoft.android.budget.common.me_info.entity.SettingsData
import ru.budget.android.api.data.source.network.entity.me.MeResponse
import ru.budget.android.api.data.source.network.entity.me.SettingsResponse
import ru.bysoft.android.budget.common.me_info.entity.DayOfWeek

fun mapToData(response: MeResponse) = MeData(
    name = response.name,
    email = response.email,
    settingsData = response.settingsResponse?.let { mapToSettings(it) },
    userId = response.userId,
    pictureUrl = response.settingsResponse?.pictureUrl
)

fun mapToSettings(response: SettingsResponse) =
    response.currencyResponse?.let {
        SettingsData(
            currency = it,
            firstDayOfWeek = mapToDayOfWeek(response.firstDayOfWeek)
        )
    } ?: SettingsData(
        firstDayOfWeek = mapToDayOfWeek(response.firstDayOfWeek)
    )

fun mapToDayOfWeek(dayOfWeek: String?): DayOfWeek =
    when (dayOfWeek) {
        "MON" -> DayOfWeek.MONDAY
        "TUE" -> DayOfWeek.TUESDAY
        "WED" -> DayOfWeek.WEDNESDAY
        "THU" -> DayOfWeek.THURSDAY
        "FRI" -> DayOfWeek.FRIDAY
        "SAT" -> DayOfWeek.SATURDAY
        "SUN" -> DayOfWeek.SUNDAY
        else -> DayOfWeek.MONDAY
    }