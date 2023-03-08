package ru.bysoft.budget.create_udate_category.presentation.entity

import ru.bysoft.budget.common.util.CategoryTypeEnum
import ru.bysoft.budget.common.util.PeriodState
import ru.bysoft.budget.create_udate_category.R
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
    val toastText: Int? = null,
    val typeCategory: CategoryTypeEnum = CategoryTypeEnum.EXPENSE
)

data class IconState(
    val iconName: String?
)

