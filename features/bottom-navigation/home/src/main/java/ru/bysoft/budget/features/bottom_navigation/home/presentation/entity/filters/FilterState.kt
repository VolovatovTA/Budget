package ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.filters

import androidx.compose.runtime.Stable

@Stable
data class FilterState(
    val listFilters: List<FilterData>
)

data class FilterData(
    val type: TypeFilter,
    val isChecked: Boolean = false,
    val isEnabled: Boolean = true
)

enum class TypeFilter(val text: String, val nameForBack: String) {
    In("доход","INCOME"),
    Out("расход","EXPENSE"),
    Transfer("перевод","TRANSFER"),
}