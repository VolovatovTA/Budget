package ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.transactions

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import ru.bysoft.budget.common.util.BudgetCurrency

sealed interface TransactionsState

@Immutable
data class TransactionSuccess(
    val list: List<TransactionInfo>
) : TransactionsState

data class TransactionLoading(
    val isRefreshing: Boolean
) : TransactionsState

object TransactionError : TransactionsState

data class TransactionInfo(
    val name: String,
    val icon: ImageVector?,
    val date: String?,
    val amount: String,
    val currency: BudgetCurrency,
    val color: String,
    val id: String
)