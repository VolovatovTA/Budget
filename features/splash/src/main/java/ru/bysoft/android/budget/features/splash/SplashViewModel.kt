package ru.bysoft.android.budget.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.common.token.ITokenStorage
import ru.bysoft.android.budget.features.currency_rates.data.ICurrencyRatesRepo
import ru.bysoft.android.budget.features.splash.navigation.ISplashNavigation
import javax.inject.Inject

interface ISplashViewModel {
    fun onAnimationFinished()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenRepo: ITokenStorage,
    private val currencyRatesRepo: ICurrencyRatesRepo,
    private val navigate: ISplashNavigation,
    private val errorLogger: IErrorLogger
) : ViewModel(), ISplashViewModel {

    private val handlerException = CoroutineExceptionHandler { _, throwable ->
        errorLogger.logError(throwable)
    }

    init {
        viewModelScope.launch(handlerException) {
            currencyRatesRepo.getCurrencyRates()
        }
    }

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