package ru.bysoft.android.budget.common.me_info.entity

data class MeData(
    val email: String,
    val name: String,
    val settingsData: SettingsData,
    val userId: String
)

data class SettingsData(
    val currency: String
)