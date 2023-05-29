package ru.bysoft.android.budget.features.splash

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.bysoft.android.budget.common.token.ITokenStorage
import ru.bysoft.android.budget.features.splash.navigation.ISplashNavigation
import javax.inject.Inject

interface ISplashViewModel {
    fun onAnimationFinished()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenRepo: ITokenStorage,
    private val navigate: ISplashNavigation,
) : ViewModel(), ISplashViewModel {

    override fun onAnimationFinished() {
        if (tokenRepo.getTokens() == null) {
            navigate.toAuth()
        } else {
            navigate.toBottomNavigation()
        }
    }


}