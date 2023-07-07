package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.transactions

import androidx.compose.material.DismissState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum

sealed interface TransactionsState

@Immutable
data class TransactionSuccess(
    val list: List<TransactionInfo>
) : TransactionsState

data class TransactionLoading(
    val isRefreshing: Boolean
) : TransactionsState

object TransactionError : TransactionsState

data class TransactionInfo @OptIn(ExperimentalMaterialApi::class) constructor(
    val name: String?,
    val icons: List<ImageVector>,
    val date: String?,
    val amount: String,
    val currency: BudgetCurrencyEnum,
    val color: String,
    val id: String,
    val isWaiting: Boolean,
    val dismissState: DismissState,
)