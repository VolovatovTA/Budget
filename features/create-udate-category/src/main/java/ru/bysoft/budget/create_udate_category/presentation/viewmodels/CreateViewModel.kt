package ru.bysoft.budget.create_udate_category.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.bysoft.budget.common.errors.IErrorLogger
import ru.bysoft.budget.common.me_info.IMeInfo
import ru.bysoft.budget.common.util.TAG
import ru.bysoft.budget.common.util.getCurrency
import ru.bysoft.budget.create_udate_category.CreateUpdateCategoryViewModel
import ru.bysoft.budget.create_udate_category.ICreateCategoryViewModel
import ru.bysoft.budget.create_udate_category.data.ICategoryRepo
import ru.bysoft.budget.create_udate_category.data.entity.CategoryErrorType
import ru.bysoft.budget.create_udate_category.data.entity.ErrorCategoryCreate
import ru.bysoft.budget.create_udate_category.data.entity.SuccessCategoryCreate
import ru.bysoft.budget.create_udate_category.data.network.entity.CategoryRequest
import ru.bysoft.budget.create_udate_category.navigation.ICreateUpdateCategoryNavigation
import ru.bysoft.budget.create_udate_category.presentation.entity.CreateUpdateCategoryState
import ru.bysoft.budget.uikit.components.currecyfield.entity.CurrencyFieldState
import javax.inject.Inject


@HiltViewModel
class CreateCategoryViewModel @Inject constructor(
    private val meInfo: IMeInfo,
    private val repo: ICategoryRepo,
    private val errorLogger: IErrorLogger,
    private val navigate: ICreateUpdateCategoryNavigation
) : CreateUpdateCategoryViewModel(navigate), ICreateCategoryViewModel {

    private val handler = CoroutineExceptionHandler { _, t ->
        errorLogger.logError(t)
    }

    override val state: MutableStateFlow<CreateUpdateCategoryState> = MutableStateFlow(
        CreateUpdateCategoryState(
            currencyFieldState = CurrencyFieldState(
                selectedCurrency = getCurrency(meInfo.getCurrentMeInfo()?.settingsData?.currency)
            )
        )
    )


    override fun onClickCreate() {
        viewModelScope.launch(handler) {
            state.value = state.value.copy(isLoading = true)
            val categoryData = repo.createCategory(
                CategoryRequest(
                    iconName = state.value.iconState.iconName,
                    currency = state.value.currencyFieldState.selectedCurrency!!.iso4217,
                    name = state.value.nameTextState.text,
                ),
                "expenses"
            )
            state.value = state.value.copy(isLoading = false)
            when (categoryData) {
                is SuccessCategoryCreate -> {
                    navigate.back()
                }
                is ErrorCategoryCreate -> {
                    updateState(categoryData)
                }
            }
            Log.d(TAG, "onClickCreate: $categoryData")
        }
    }
}