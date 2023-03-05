package ru.bysoft.budget.create_udate_category.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.bysoft.budget.common.util.BudgetCurrency
import ru.bysoft.budget.common.util.PeriodState
import ru.bysoft.budget.create_udate_category.data.entity.CategoryErrorType
import ru.bysoft.budget.create_udate_category.data.entity.ErrorCategoryCreate
import ru.bysoft.budget.create_udate_category.navigation.ICreateUpdateCategoryNavigation
import ru.bysoft.budget.create_udate_category.presentation.entity.CategoryTypeEnum
import ru.bysoft.budget.create_udate_category.presentation.entity.CreateUpdateCategoryState
import ru.bysoft.budget.create_udate_category.presentation.entity.IconState
import ru.bysoft.budget.uikit.components.currecyfield.entity.CurrencyFieldState

interface ICreateUpdateCategoryViewModel {
    val state: StateFlow<CreateUpdateCategoryState>
    fun onCurrencySelected(currency: BudgetCurrency)
    fun onPeriodSelected(newPeriod: PeriodState)
    fun onNameChanged(newName: String)
    fun onIconSelected(iconName: String?)
    fun onAmountChanged(newAmount: String)
    fun setCategoryType(type: CategoryTypeEnum)
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

    override fun setCategoryType(type: CategoryTypeEnum) {
        state.update { it.copy(typeCategory = type) }
    }

    override fun onCurrencySelected(currency: BudgetCurrency) {
        state.update {
            it.copy(
                currencyFieldState = it.currencyFieldState.copy(
                    selectedCurrency = currency
                )
            )
        }
    }

    override fun onPeriodSelected(newPeriod: PeriodState) {
        state.update {
            it.copy(
                periodState = it.periodState.copy(
                    selectedValue = newPeriod
                )
            )
        }
    }

    override fun onNameChanged(newName: String) {
        state.update {
            it.copy(
                nameTextState = it.nameTextState.copy(
                    text = newName
                )
            )
        }
    }

    override fun onIconSelected(iconName: String?) {
        state.update { it.copy(iconState = IconState(iconName)) }
    }

    override fun onAmountChanged(newAmount: String) {
        state.update {
            it.copy(
                amountTextState = it.amountTextState.copy(
                    text = newAmount
                )
            )
        }
    }

    fun updateState(categoryData: ErrorCategoryCreate) {
        when (categoryData.errorType) {
            CategoryErrorType.INVALID_ICON_NAME ->
                state.update { it.copy(toastText = "Что-то не понравилось с иконкой... Хотя что там могло не понравиться") }
            CategoryErrorType.TECHNICAL_ERROR_IN_BACK ->
                state.update { it.copy(toastText = "Чегот сломались...") }
            CategoryErrorType.UNKNOWN_ERROR ->
                state.update { it.copy(toastText = "Неизвестная ошибка") }
            CategoryErrorType.NULL_ERROR ->
                state.update { it.copy(toastText = "Сервер ответил ошибкой, но без подробностей..") }
            CategoryErrorType.INVALID_CURRENCY_ ->
                state.update {
                    it.copy(
                        currencyFieldState = state.value.currencyFieldState.copy(errorText = "Недопустимая валюта")
                    )
                }
            CategoryErrorType.INVALID_NAME_____ ->
                state.update {
                    it.copy(
                        nameTextState = state.value.nameTextState.copy(errorText = "Недопустимое имя")
                    )
                }
            CategoryErrorType.NO_UNIQ_NAME_____ ->
                state.update {
                    it.copy(
                        nameTextState = state.value.nameTextState.copy(errorText = "Нужно уникальное имя")
                    )
                }
        }
    }

    override fun back() = navigate.back()

}