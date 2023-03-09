package ru.bysoft.android.budget.features.bottom_navigation.home.data.me.mapper

import ru.bysoft.android.budget.common.me_info.entity.MeData
import ru.bysoft.android.budget.common.me_info.entity.SettingsData
import ru.bysoft.android.budget.features.bottom_navigation.home.data.me.network.entity.MeResponse
import ru.bysoft.android.budget.features.bottom_navigation.home.data.me.network.entity.SettingsResponse

fun MeResponse.mapToData() = MeData(
    name = this.name,
    email = this.email,
    settingsData = settingsResponse.mapToSettings(),
    userId = userId
)

fun SettingsResponse.mapToSettings() = SettingsData(currencyResponse)