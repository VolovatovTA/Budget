package ru.bysoft.android.budget.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.common.token.ITokenStorage
import ru.bysoft.android.budget.features.splash.navigation.ISplashNavigation

interface ISplashViewModel {
    fun onAnimationFinished()
}

class SplashViewModel(
    private val tokenRepo: ITokenStorage,
    private val navigate: ISplashNavigation,
) : ViewModel(), ISplashViewModel {

    override fun onAnimationFinished() {
        viewModelScope.launch {
            if (tokenRepo.getTokens().first() == null) {
                navigate.toAuth()
            } else {
                navigate.toBottomNavigation()
            }
        }
    }


}