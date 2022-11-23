package ru.bysoft.budget.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.bysoft.budget.common.errors.errorLogger
import ru.bysoft.budget.home.data.IHomeWalletsRepo
import javax.inject.Inject

interface IHomeViewModel {
    fun init(controller: NavHostController)
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: IHomeWalletsRepo
) : ViewModel(), IHomeViewModel {
    private val homeExceptionHandler = CoroutineExceptionHandler { _, t -> errorLogger.logError(t) }

    lateinit var controller: NavHostController

    override fun init(controller: NavHostController) {
        this.controller = controller
        getWallets()
    }

    private fun getWallets() {
        viewModelScope.launch(homeExceptionHandler) { repo.getWallets() }
        viewModelScope.launch(homeExceptionHandler) { repo.getWallets() }
        viewModelScope.launch(homeExceptionHandler) { repo.getWallets() }
        viewModelScope.launch(homeExceptionHandler) { repo.getWallets() }
    }


}