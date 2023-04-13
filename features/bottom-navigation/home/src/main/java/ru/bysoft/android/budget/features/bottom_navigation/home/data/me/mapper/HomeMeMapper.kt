package ru.bysoft.android.budget.features.bottom_navigation.home.data.me.mapper

import ru.bysoft.android.budget.common.me_info.entity.MeData
import ru.bysoft.android.budget.common.me_info.entity.SettingsData
import ru.bysoft.android.budget.features.bottom_navigation.home.data.me.network.entity.MeResponse
import ru.bysoft.android.budget.features.bottom_navigation.home.data.me.network.entity.SettingsResponse

fun mapToData(response: MeResponse) = MeData(
    name = response.name,
    email = response.email,
    settingsData = mapToSettings(response.settingsResponse),
    userId = response.userId
)

fun mapToSettings(response: SettingsResponse) = SettingsData(response.currencyResponse)