package ru.bysoft.budget.create_udate_category.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.bysoft.budget.common.errors.IErrorLogger
import ru.bysoft.budget.common.errors.exceptionHandler
import ru.bysoft.budget.common.me_info.IMeInfo
import ru.bysoft.budget.common.util.getCurrency
import ru.bysoft.budget.create_udate_category.R
import ru.bysoft.budget.create_udate_category.data.ICategoryRepo
import ru.bysoft.budget.create_udate_category.data.entity.ErrorCategoryCreate
import ru.bysoft.budget.create_udate_category.data.entity.SuccessCategoryCreate
import ru.bysoft.budget.create_udate_category.data.network.entity.CategoryRequest
import ru.bysoft.budget.create_udate_category.navigation.ICreateUpdateCategoryNavigation
import ru.bysoft.budget.create_udate_category.presentation.entity.CreateUpdateCategoryState
import ru.bysoft.budget.uikit.components.currecyfield.entity.CurrencyFieldState
import javax.inject.Inject


@HiltViewModel
class UpdateCategoryViewModel @Inject constructor(
    private val meInfo: IMeInfo,
    private val repo: ICategoryRepo,
    private val errorLogger: IErrorLogger,
    private val navigate: ICreateUpdateCategoryNavigation
) : CreateUpdateCategoryViewModel(navigate), IUpdateCategoryViewModel {

    private var id = ""
    override val state: MutableStateFlow<CreateUpdateCategoryState> = MutableStateFlow(
        CreateUpdateCategoryState(
            currencyFieldState = CurrencyFieldState(
                selectedCurrency = getCurrency(meInfo.getCurrentMeInfo()?.settingsData?.currency)
            )
        )
    )


    override fun onClickSave() {
        viewModelScope.launch(exceptionHandler) {
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
            if (data is SuccessCategoryCreate){
                navigate.back()
            } else {
                updateState(data as ErrorCategoryCreate)
            }
        }
    }

    override fun initId(id: String) {
        this.id = id
        viewModelScope.launch(exceptionHandler) {
            state.value = state.value.copy(isLoading = true)
            val data = repo.getCategoryInfo(id, "expenses")
            state.value = state.value.copy(
                isLoading = false,
                nameTextState = state.value.nameTextState.copy(text = data.name),
                iconState = state.value.iconState.copy(iconName = data.iconName),
                currencyFieldState = state.value.currencyFieldState.copy(
                    selectedCurrency = getCurrency(data.currency)
                ),
            )
        }
    }

    override fun delete() {
        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO){
                state.value = state.value.copy(isLoading = true)
                repo.delete(id, "expenses")
                state.value = state.value.copy(toastText = R.string.category_deleted)
            }
            navigate.back()
        }
    }
}