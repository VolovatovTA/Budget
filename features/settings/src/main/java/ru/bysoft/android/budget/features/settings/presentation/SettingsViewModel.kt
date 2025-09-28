package ru.bysoft.android.budget.features.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.dsl.module
import ru.bysoft.android.budget.common.errors.errorLogger
import ru.bysoft.android.budget.common.me_info.IMeInfo
import ru.bysoft.android.budget.common.me_info.entity.DayOfWeek
import ru.bysoft.android.budget.common.token.ITokenStorage
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.currency.getCurrency
import ru.bysoft.android.budget.features.settings.data.SettingsRepository
import ru.bysoft.android.budget.features.settings.presentation.entity.SettingsState
import ru.bysoft.android.budget.features.settings.presentation.navigation.ISettingsNavigation
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.CurrencyFieldState
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.PopupFieldState
import ru.bysoft.android.budget.features.settings.R
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf


val SettingDi = module {
    viewModelOf(::SettingsViewModel)
}

class SettingsViewModel(
    meInfo: IMeInfo,
    private val tokenRepo: ITokenStorage,
    private val navigate: ISettingsNavigation,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val toastState = MutableSharedFlow<Int>()
    private val loadedData = meInfo.getCurrentMeInfo()
    private val _state = MutableStateFlow(
        SettingsState(
            meInfoData = loadedData,
            dayOfWeekState = PopupFieldState(
                selectedValue = meInfo.getCurrentMeInfo()?.settingsData?.firstDayOfWeek,
                list = listOf(
                    DayOfWeek.MONDAY,
                    DayOfWeek.SUNDAY
                )
            ),
            currencyFieldState = CurrencyFieldState(
                selectedCurrency = getCurrency(loadedData?.settingsData?.currency)
            )
        )
    )

    val state = _state.asStateFlow()

    fun onLogoutClick() {
        viewModelScope.launch {
            tokenRepo.clearTokens()
            navigate.toAuth()
        }
    }

    fun onDeleteAccountClick() {
        viewModelScope.launch {
            settingsRepository.deleteAccount()
            tokenRepo.clearTokens()
            navigate.toAuth()
        }
    }

    fun onEditCurrency(currency: BudgetCurrencyEnum) {
        _state.update {
            it.copy(
                currencyFieldState = it.currencyFieldState.copy(selectedCurrency = currency)
            )
        }
        showConfirmIfNeed()
    }

    fun onEditFirstDayOfWeek(dayOfWeek: DayOfWeek) {
        _state.update {
            it.copy(
                dayOfWeekState = it.dayOfWeekState.copy(selectedValue = dayOfWeek)
            )
        }
        showConfirmIfNeed()
    }

    private fun showConfirmIfNeed() {
        _state.update {
            it.copy(
                isDataChanged = it.dayOfWeekState.selectedValue != loadedData?.settingsData?.firstDayOfWeek
                        || it.currencyFieldState.selectedCurrency?.iso4217 != loadedData?.settingsData?.currency
            )
        }
    }

    private val handler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch { toastState.emit(R.string.error_while_update_profile_data) }
        _state.update { it.copy(isLoading = false) }
        errorLogger.logError(throwable)
        navigate.popBack()
    }

    fun onConfirm() {
        viewModelScope.launch(handler) {
            _state.update { it.copy(isLoading = true) }
            settingsRepository.setMeInfo(
                state.value.currencyFieldState.selectedCurrency,
                state.value.dayOfWeekState.selectedValue,
                state.value.meInfoData?.pictureUrl
            )
            _state.update { it.copy(isLoading = false) }
            navigate.popBack()
        }
    }
}