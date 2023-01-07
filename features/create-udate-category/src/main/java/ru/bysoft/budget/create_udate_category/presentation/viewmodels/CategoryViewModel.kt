package ru.bysoft.budget.create_udate_category

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.bysoft.budget.common.util.BudgetCurrency
import ru.bysoft.budget.common.util.TAG
import ru.bysoft.budget.common.util.getCurrency
import ru.bysoft.budget.create_udate_category.data.entity.CategoryErrorType
import ru.bysoft.budget.create_udate_category.data.entity.ErrorCategoryCreate
import ru.bysoft.budget.create_udate_category.navigation.ICreateUpdateCategoryNavigation
import ru.bysoft.budget.create_udate_category.presentation.entity.CreateUpdateCategoryState
import ru.bysoft.budget.create_udate_category.presentation.entity.IconState
import ru.bysoft.budget.create_udate_category.presentation.entity.PeriodState
import ru.bysoft.budget.uikit.components.currecyfield.entity.CurrencyFieldState

interface ICreateUpdateCategoryViewModel {
    val state: StateFlow<CreateUpdateCategoryState>
    fun onCurrencySelected(currency: BudgetCurrency)
    fun onPeriodSelected(newPeriod: PeriodState)
    fun onNameChanged(newName: String)
    fun onIconSelected(iconName: String?)
    fun onAmountChanged(newAmount: String)
    fun back()
}

interface ICreateCategoryViewModel : ICreateUpdateCategoryViewModel {
    fun onClickCreate()
}

interface IUpdateCategoryViewModel : ICreateUpdateCategoryViewModel {
    fun onClickSave()
    fun initId(id: String)
    fun delete()
}

abstract class CreateUpdateCategoryViewModel(
    private val navigate: ICreateUpdateCategoryNavigation
) : ViewModel(), ICreateUpdateCategoryViewModel {

    override val state: MutableStateFlow<CreateUpdateCategoryState> = MutableStateFlow(
        CreateUpdateCategoryState(currencyFieldState = CurrencyFieldState(selectedCurrency = null))
    )

    override fun onCurrencySelected(currency: BudgetCurrency) {
        state.value = state.value.copy(
            currencyFieldState = state.value.currencyFieldState.copy(
                selectedCurrency = currency
            )
        )
    }

    override fun onPeriodSelected(newPeriod: PeriodState) {
        state.value = state.value.copy(
            periodState = state.value.periodState.copy(
                selectedValue = newPeriod
            )
        )
    }

    override fun onNameChanged(newName: String) {
        state.value = state.value.copy(
            nameTextState = state.value.nameTextState.copy(
                text = newName
            )
        )
    }

    override fun onIconSelected(iconName: String?) {
        Log.d(TAG, "onIconSelected: $iconName")
        state.value = state.value.copy(
            iconState = IconState(iconName)
        )
    }

    override fun onAmountChanged(newAmount: String) {
        state.value = state.value.copy(
            amountTextState = state.value.amountTextState.copy(
                text = newAmount
            )
        )
    }

    fun updateState(categoryData: ErrorCategoryCreate) {
        when (categoryData.errorType) {
            CategoryErrorType.INVALID_ICON_NAME -> {
                state.value = state.value.copy(
                    toastText = "Что-то не понравилось с иконкой... Хотя что там могло не понравиться"
                )
            }
            CategoryErrorType.TECHNICAL_ERROR_IN_BACK -> {
                state.value = state.value.copy(
                    toastText = "Чегот сломались..."
                )
            }
            CategoryErrorType.UNKNOWN_ERROR -> {
                state.value = state.value.copy(
                    toastText = "Неизвестная ошибка"
                )
            }
            CategoryErrorType.NULL_ERROR -> {
                state.value = state.value.copy(
                    toastText = "Сервер ответил ошибкой, но без подробностей.."
                )
            }
            CategoryErrorType.INVALID_CURRENCY_ -> {
                state.value = state.value.copy(
                    currencyFieldState = state.value.currencyFieldState.copy(
                        errorText = "Недопустимая валюта"
                    )
                )
            }
            CategoryErrorType.INVALID_NAME_____ -> {
                state.value = state.value.copy(
                    nameTextState = state.value.nameTextState.copy(
                        errorText = "Недопустимое имя"
                    )
                )
            }
            CategoryErrorType.NO_UNIQ_NAME_____ -> {
                state.value = state.value.copy(
                    nameTextState = state.value.nameTextState.copy(
                        errorText = "Нужно уникальное имя"
                    )
                )
            }
        }
    }

    override fun back() = navigate.back()


}