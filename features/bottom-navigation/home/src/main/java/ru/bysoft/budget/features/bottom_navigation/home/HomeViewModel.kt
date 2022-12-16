package ru.bysoft.budget.features.bottom_navigation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.bysoft.budget.common.errors.errorLogger
import ru.bysoft.budget.common.me_info.IMeInfo
import ru.bysoft.budget.features.bottom_navigation.home.data.me.IHomeMeRepo
import ru.bysoft.budget.features.bottom_navigation.home.data.wallets.IHomeWalletsRepo
import ru.bysoft.budget.features.bottom_navigation.home.navigation.IHomeNavigation
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.*
import ru.bysoft.budget.features.bottom_navigation.home.presentation.mapper.mapToState
import javax.inject.Inject

interface IHomeViewModel {
    val walletsState: StateFlow<IWalletsState>
    val meState: StateFlow<IMeState>
    fun loadData()
    fun onClickSimpleWallet()
    fun onClickCreateWallet()
    fun onClickEditWallet(walletId: String)
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val walletsRepo: IHomeWalletsRepo,
    private val meRepo: IHomeMeRepo,
    private val navigate: IHomeNavigation,
    private val meInfo: IMeInfo
) : ViewModel(), IHomeViewModel {

    override fun loadData() {
        getMeInfo()
    }

    private val homeWalletsExceptionHandler = CoroutineExceptionHandler { _, t ->
        errorLogger.logError(t)
        walletsState.value = WalletsErrorState
    }

    private val homeMeExceptionHandler = CoroutineExceptionHandler { _, t ->
        errorLogger.logError(t)
        meState.value = MeErrorState
    }


    override val walletsState: MutableStateFlow<IWalletsState> =
        MutableStateFlow(WalletsLoadingState)
    override val meState: MutableStateFlow<IMeState> =
        MutableStateFlow(MeLoadingState)

    override fun onClickSimpleWallet() {

    }

    override fun onClickCreateWallet() {
        val currentWalletState = walletsState.value
        if (currentWalletState is WalletsSuccessState) {
            navigate.toCreateWallet()
        }
    }

    override fun onClickEditWallet(walletId: String) {

    }

    private fun getWallets() {
        viewModelScope.launch(homeWalletsExceptionHandler) {
            walletsState.value = WalletsLoadingState
            walletsState.value = WalletsSuccessState(walletsRepo.getWallets().mapToState())
        }
    }

    private fun getMeInfo() {
        viewModelScope.launch(homeMeExceptionHandler) {
            meState.value = MeLoadingState
            val meInfoData = meRepo.getMeInfo()
            meInfo.setCurrentMeInfo(meInfoData)
            meState.value = MeSuccessState(meInfoData.name)
            getWallets()
        }
    }

}