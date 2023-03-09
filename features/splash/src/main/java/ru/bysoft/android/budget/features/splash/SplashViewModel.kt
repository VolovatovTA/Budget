package ru.bysoft.android.budget.features.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.bysoft.android.budget.common.token.ITokenRepo
import ru.bysoft.android.budget.common.util.TAG
import ru.bysoft.android.budget.common.util.toJson
import ru.bysoft.android.budget.features.splash.navigation.ISplashNavigation
import javax.inject.Inject

interface ISplashViewModel {
    fun onAnimationFinished()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenRepo: ITokenRepo,
    private val navigate: ISplashNavigation,
) : ViewModel(), ISplashViewModel {

    override fun onAnimationFinished() {
        val tokens = tokenRepo.getTokens()
//        tokens?.let {
//            tokenRepo.saveTokens(tokenData = tokens.copy(accessToken = "huinya"))
//        }
        Log.d(TAG, "getTokens: ${tokens.toJson()}")
        if (tokens == null) {
            navigate.toAuth()
        } else {
            navigate.toBottomNavigation()
        }
    }


}