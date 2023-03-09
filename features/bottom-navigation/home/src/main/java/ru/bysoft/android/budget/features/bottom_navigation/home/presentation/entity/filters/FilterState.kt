package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.filters

import androidx.compose.runtime.Stable
import ru.bysoft.android.budget.features.bottom_navigation.home.R

@Stable
data class FilterState(
    val listFilters: List<FilterData>
) {
    fun isTransfersChecked() =
        listFilters.find { it.type == TypeFilter.Transfer }?.isChecked ?: false

    fun isExpensesChecked() = listFilters.find { it.type == TypeFilter.Out }?.isChecked ?: false

    fun isIncomeChecked() = listFilters.find { it.type == TypeFilter.In }?.isChecked ?: false
}

data class FilterData(
    val type: TypeFilter,
    val isChecked: Boolean = false,
    val isEnabled: Boolean = true
)

enum class TypeFilter(val text: Int, val nameForBack: String) {
    In(R.string.filter_income, "INCOME"),
    Out(R.string.filter_expense, "EXPENSE"),
    Transfer(R.string.filter_transfer, "TRANSFER"),
}