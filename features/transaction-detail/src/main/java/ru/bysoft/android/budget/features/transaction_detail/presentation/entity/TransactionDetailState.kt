package ru.bysoft.android.budget.features.transaction_detail.presentation.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

sealed interface TransactionDetailState {
    data object Loading : TransactionDetailState
    data object NotFound : TransactionDetailState
    data object Error : TransactionDetailState

    @Immutable
    data class Success(
        @param:StringRes val typeText: Int,
        val amount: String,
        val date: String?,
        val comment: String?,
        val categories: List<CategoryItem>,
        val isDeleting: Boolean = false,
    ) : TransactionDetailState
}

@Immutable
data class CategoryItem(val name: String, @param:DrawableRes val icon: Int?)
