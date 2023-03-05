package ru.bysoft.budget.uikit.components.rowtab.entity

import androidx.compose.runtime.Stable
import ru.bysoft.budget.uikit.components.buttons.entity.UiKitButtonInfo

@Stable
data class UiKitRowTabState<T>(
    val listFilters: List<UiKitTabInfo>
)

data class UiKitTabInfo(
    val info: UiKitButtonInfo,
    val isChecked: Boolean = false,
    val isEnabled: Boolean = true
)