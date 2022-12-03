package ru.bysoft.budget.home.presentation.entity

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
    val list: List<WalletState>
) : IWalletsState

object WalletsErrorState : IWalletsState

object WalletsLoadingState : IWalletsState

data class WalletState(
    val name: String,
    val backgroundColor: String,
    val currency: String,
    val balance: String,
)