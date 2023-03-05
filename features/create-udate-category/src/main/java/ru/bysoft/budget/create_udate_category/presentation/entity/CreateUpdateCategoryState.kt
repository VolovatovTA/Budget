package ru.bysoft.budget.create_udate_category.presentation.entity

import ru.bysoft.budget.common.util.PeriodState
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
        selectedValue = PeriodState.NO_PERIOD,
        list = listOf(
            PeriodState.DAY,
            PeriodState.WEEK,
            PeriodState.MONTH,
            PeriodState.NO_PERIOD,
        ),
    ),
    val toastText: String? = null,
    val typeCategory: CategoryTypeEnum = CategoryTypeEnum.EXPENSE
)

data class IconState(
    val iconName: String?
)

enum class CategoryTypeEnum(val text: String) {
    INCOME("Доход"), EXPENSE("Расход");
}

