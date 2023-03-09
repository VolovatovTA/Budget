package ru.bysoft.android.budget.features.bottom_navigation.home.presentation.entity.wallets

interface IWalletsState

data class WalletsSuccessState(
    val list: List<IWalletPresentation>,
) : IWalletsState

object WalletsErrorState : IWalletsState

data class WalletsLoadingState(
    val isRefreshing: Boolean
) : IWalletsState

sealed interface IWalletPresentation

data class WalletCardPresentation(
    val walletId: String,
    val name: String,
    val backgroundColor: String,
    val currency: String,
    val balance: String
) : IWalletPresentation

object WalletCreateNewPresentation : IWalletPresentation