package ru.bysoft.android.budget.features.create_udate_category.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.bysoft.android.budget.common.data_entity.CategoryErrorType
import ru.bysoft.android.budget.currency.BudgetCurrency
import ru.bysoft.android.budget.common.util.CategoryTypeEnum
import ru.bysoft.android.budget.common.util.PeriodState
import ru.bysoft.android.budget.features.create_udate_category.R
import ru.bysoft.android.budget.features.create_udate_category.navigation.CreateCategoryNavInfo
import ru.bysoft.android.budget.features.create_udate_category.navigation.ICreateUpdateCategoryNavigation
import ru.bysoft.android.budget.features.create_udate_category.presentation.entity.CreateUpdateCategoryState
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.CurrencyFieldState
import ru.bysoft.android.budget.uikit.components.icon_component.UiKitIconState

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
    fun initNavParams(typeCategory: CreateCategoryNavInfo?)
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
        state.update { it.copy(iconState = UiKitIconState(iconName)) }
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

    fun updateState(categoryData: CategoryErrorType?) {
        when (categoryData) {
            CategoryErrorType.INVALID_ICON_NAME ->
                state.update { it.copy(toastText = R.string.invalid_icon_name) }
            CategoryErrorType.TECHNICAL_ERROR_IN_BACK ->
                state.update { it.copy(toastText = R.string.technical_error) }
            CategoryErrorType.UNKNOWN_ERROR ->
                state.update { it.copy(toastText = R.string.undefined_error) }
            CategoryErrorType.NULL_ERROR ->
                state.update { it.copy(toastText = R.string.null_error_server_sent) }
            CategoryErrorType.INVALID_CURRENCY ->
                state.update {
                    it.copy(
                        currencyFieldState = state.value.currencyFieldState.copy(errorText = R.string.invalid_currency)
                    )
                }
            CategoryErrorType.INVALID_NAME ->
                state.update {
                    it.copy(
                        nameTextState = state.value.nameTextState.copy(errorText = R.string.invalid_name)
                    )
                }
            CategoryErrorType.NO_UNIQ_NAME ->
                state.update {
                    it.copy(
                        nameTextState = state.value.nameTextState.copy(errorText = R.string.no_unique_name)
                    )
                }
        }
    }

    override fun back() = navigate.back()

}