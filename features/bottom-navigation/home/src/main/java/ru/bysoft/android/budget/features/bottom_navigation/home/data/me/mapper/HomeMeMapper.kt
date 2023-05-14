package ru.bysoft.android.budget.features.bottom_navigation.home.data.me.mapper

import ru.bysoft.android.budget.common.me_info.entity.MeData
import ru.bysoft.android.budget.common.me_info.entity.SettingsData
import ru.budget.android.api.data.source.network.entity.me.MeResponse
import ru.budget.android.api.data.source.network.entity.me.SettingsResponse

fun mapToData(response: MeResponse) = MeData(
    name = response.name,
    email = response.email,
    settingsData = mapToSettings(response.settingsResponse),
    userId = response.userId,
    pictureUrl = response.pictureUrl.orEmpty()
)

fun mapToSettings(response: SettingsResponse) = SettingsData(response.currencyResponse)