package ru.bysoft.android.budget.features.create_udate_category.presentation.viewmodels

import ru.bysoft.android.budget.common.errors.handler
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.currency.getAvailableCurrency
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.budget.android.api.data.source.network.entity.category.CategoryRequest
import ru.bysoft.android.budget.common.me_info.IMeInfo
import ru.bysoft.android.budget.currency.getCurrency
import ru.bysoft.android.budget.features.create_udate_category.R
import ru.bysoft.android.budget.features.create_udate_category.data.ICategoryRepo
import ru.bysoft.android.budget.common.data_entity.CategoryErrorType
import ru.bysoft.android.budget.common.util.PeriodState
import ru.bysoft.android.budget.features.create_udate_category.navigation.ICreateUpdateCategoryNavigation
import ru.bysoft.android.budget.features.create_udate_category.presentation.entity.CreateUpdateCategoryState
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.CurrencyFieldState


class UpdateCategoryViewModel(
    private val errorLogger: IErrorLogger,
    meInfo: IMeInfo,
    private val repo: ICategoryRepo,
    private val navigate: ICreateUpdateCategoryNavigation
) : CreateUpdateCategoryViewModel(navigate), IUpdateCategoryViewModel {

    private val handler = errorLogger.handler()

    private var id = ""
    override val state: MutableStateFlow<CreateUpdateCategoryState> = MutableStateFlow(
        CreateUpdateCategoryState(
            currencyFieldState = CurrencyFieldState(
                selectedCurrency = getCurrency(meInfo.getCurrentMeInfo()?.settingsData?.currency),
                list = getAvailableCurrency(),
            )
        )
    )


    override fun onClickSave() {
        viewModelScope.launch(handler) {
            state.value = state.value.copy(isLoading = true)
            val data = repo.updateCategory(
                CategoryRequest(
                    iconName = state.value.iconState.iconName,
                    currency = state.value.currencyFieldState.selectedCurrency!!.iso4217,
                    name = state.value.nameTextState.text,
                    limitAmount = state.value.amountTextState.text.toFloatOrNull(),
                    limitType = state.value.periodState.selectedValue?.textToBack
                ),
                "expenses",
                id
            )
            if (data.isSuccess) {
                navigate.back()
            } else {
                updateState(data.exceptionOrNull() as? CategoryErrorType)
            }
        }
    }

    override fun initId(id: String) {
        if (this.id == id) return
        this.id = id
        viewModelScope.launch(handler) {
            state.value = state.value.copy(isLoading = true)
            val data = repo.getCategoryInfo(id, "expenses")
            state.value = state.value.copy(
                isLoading = false,
                nameTextState = state.value.nameTextState.copy(text = data.name),
                iconState = state.value.iconState.copy(iconName = data.iconName),
                currencyFieldState = state.value.currencyFieldState.copy(
                    selectedCurrency = getCurrency(data.currency)
                ),
                amountTextState = state.value.amountTextState.copy(text = data.limitAmount.toString()),
                periodState = state.value.periodState.copy(
                    selectedValue = PeriodState.getByTextFromBack(
                        data.limitType ?: ""
                    )
                )
            )
        }
    }

    override fun delete() {
        viewModelScope.launch(handler) {
            withContext(Dispatchers.IO) {
                state.value = state.value.copy(isLoading = true)
                repo.delete(id, "expenses")
                state.value = state.value.copy(toastText = R.string.category_deleted)
            }
            navigate.back()
        }
    }
}