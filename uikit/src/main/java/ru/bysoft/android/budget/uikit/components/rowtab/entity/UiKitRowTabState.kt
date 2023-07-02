package ru.bysoft.android.budget.uikit.components.rowtab.entity

import androidx.annotation.StringRes

data class UiKitRowTabState(
    val listFilters: List<UiKitTabInfo>
)

data class UiKitTabInfo(
    @StringRes val text: Int,
    val isChecked: Boolean = false,
    val isEnabled: Boolean = true
)