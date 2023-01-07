package ru.bysoft.budget.create_udate_category.presentation.entity

import ru.bysoft.budget.uikit.components.currecyfield.entity.CurrencyFieldState
import ru.bysoft.budget.uikit.components.currecyfield.entity.PopupFieldState
import ru.bysoft.budget.uikit.components.textfield.TextFieldState

data class CreateUpdateCategoryState(
    val nameTextState: TextFieldState = TextFieldState(),
    val amountTextState: TextFieldState = TextFieldState(),
    val currencyFieldState: CurrencyFieldState,
    val iconState: IconState = IconState(null),
    val isLoading: Boolean = false,
    val periodState: PopupFieldState<PeriodState> = PopupFieldState(
        selectedValue = PeriodState.WEEK,
        list = listOf(
            PeriodState.DAY,
            PeriodState.WEEK,
            PeriodState.TWO_WEEKS,
            PeriodState.MONTH,
        ),
    ),
    val toastText: String? = null
)

data class IconState(
    val iconName: String?
)

enum class PeriodState(val textToShow: String, val textToBack: String) {
    DAY("в день", ""),
    WEEK("в неделю", ""),
    TWO_WEEKS("в две недели", ""),
    MONTH("в месяц", "");
}

