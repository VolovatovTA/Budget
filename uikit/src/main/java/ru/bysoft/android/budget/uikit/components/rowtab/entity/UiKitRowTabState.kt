package ru.bysoft.android.budget.uikit.components.rowtab.entity

import androidx.compose.runtime.Stable

@Stable
data class UiKitRowTabState(
    val listFilters: List<UiKitTabInfo>
)

data class UiKitTabInfo(
    val text: String,
    val isChecked: Boolean = false,
    val isEnabled: Boolean = true
)