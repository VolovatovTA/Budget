package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets

import androidx.compose.runtime.Immutable
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum

@Immutable
sealed class IWalletsState(
    open val isRefreshing: Boolean
)

@Immutable
data class WalletsSuccessState(
    val list: List<IWalletPresentation>,
    val currentWalletId: String,
    override val isRefreshing: Boolean = false
) : IWalletsState(isRefreshing)

object WalletsErrorState : IWalletsState(false)

data class WalletsLoadingState(
    override val isRefreshing: Boolean
) : IWalletsState(isRefreshing)

sealed interface IWalletPresentation

data class WalletCardPresentation(
    val walletId: String,
    val icon: String?,
    val name: String,
    val backgroundColor: String,
    val currency: BudgetCurrencyEnum,
    val balance: Float,
    val lastOperationDate: String
) : IWalletPresentation

object WalletCreateNewPresentation : IWalletPresentation