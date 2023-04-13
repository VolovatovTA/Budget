package ru.bysoft.android.budget.features.create_udate_category.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.common.me_info.IMeInfo
import ru.bysoft.android.budget.common.util.CategoryTypeEnum
import ru.bysoft.android.budget.common.util.getCurrency
import ru.bysoft.android.budget.features.create_udate_category.data.ICategoryRepo
import ru.bysoft.android.budget.features.create_udate_category.data.entity.ErrorCategoryCreate
import ru.bysoft.android.budget.features.create_udate_category.data.entity.SuccessCategoryCreate
import ru.bysoft.android.budget.features.create_udate_category.data.network.entity.CategoryRequest
import ru.bysoft.android.budget.features.create_udate_category.navigation.CreateCategoryNavInfo
import ru.bysoft.android.budget.features.create_udate_category.navigation.ICreateUpdateCategoryNavigation
import ru.bysoft.android.budget.features.create_udate_category.presentation.entity.CreateUpdateCategoryState
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.CurrencyFieldState
import javax.inject.Inject


@HiltViewModel
class CreateCategoryViewModel @Inject constructor(
    meInfo: IMeInfo,
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

    override fun initNavParams(typeCategory: CreateCategoryNavInfo?) {
        typeCategory?.let {
            state.value = state.value.copy(typeCategory = typeCategory.typeCategory)
        }
    }

    override fun onClickCreate() {
        viewModelScope.launch(handler) {
            state.value = state.value.copy(isLoading = true)
            val path = when (state.value.typeCategory) {
                CategoryTypeEnum.EXPENSE -> "expenses"
                CategoryTypeEnum.INCOME -> "incomes"
            }
            val categoryData = repo.createCategory(
                CategoryRequest(
                    iconName = state.value.iconState.iconName,
                    currency = state.value.currencyFieldState.selectedCurrency!!.iso4217,
                    name = state.value.nameTextState.text,
                    limitAmount = state.value.amountTextState.text.toFloatOrNull(),
                    limitType = state.value.periodState.selectedValue?.textToBack
                ),
                path
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
        }
    }
}