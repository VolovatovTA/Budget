package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets

import ru.bysoft.android.budget.currency.BudgetCurrencyEnum

interface IWalletsState

data class WalletsSuccessState(
    val list: List<IWalletPresentation>,
    val currentWalletId: String
) : IWalletsState

object WalletsErrorState : IWalletsState

data class WalletsLoadingState(
    val isRefreshing: Boolean
) : IWalletsState

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