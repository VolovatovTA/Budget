package ru.bysoft.budget.features.bottom_navigation.home.presentation.entity

data class HomeState(
    val meState: IMeState,
    val walletsState: IWalletsState,
)

sealed interface IMeState

data class MeSuccessState(
    val name: String = "",
) : IMeState

object MeErrorState : IMeState

object MeLoadingState : IMeState

interface IWalletsState

data class WalletsSuccessState(
    val list: List<IWalletPresentation>
) : IWalletsState

object WalletsErrorState : IWalletsState

object WalletsLoadingState : IWalletsState

sealed interface IWalletPresentation

data class WalletCardPresentation(
    val walletId: String,
    val name: String,
    val backgroundColor: String,
    val currency: Char,
    val balance: String
):IWalletPresentation

object WalletCreateNewPresentation:IWalletPresentation