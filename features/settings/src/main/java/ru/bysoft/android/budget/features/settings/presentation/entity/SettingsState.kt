package ru.bysoft.android.budget.features.settings.presentation.entity

import ru.bysoft.android.budget.common.me_info.entity.DayOfWeek
import ru.bysoft.android.budget.common.me_info.entity.MeData
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.CurrencyFieldState
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.PopupFieldState

data class SettingsState(
    val meInfoData: MeData?,
    val dayOfWeekState: PopupFieldState<DayOfWeek> = PopupFieldState(
        selectedValue = null,
        list = listOf(
            DayOfWeek.MONDAY,
            DayOfWeek.SUNDAY
        )
    ),
    val currencyFieldState: CurrencyFieldState = CurrencyFieldState(
        selectedCurrency = null
    ),
    val isDataChanged: Boolean = false,
    val isLoading: Boolean = false
)