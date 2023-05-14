package ru.bysoft.android.budget.features.settings.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.bysoft.android.budget.common.me_info.IMeInfo
import ru.bysoft.android.budget.common.token.ITokenRepo
import ru.bysoft.android.budget.features.settings.presentation.entity.SettingsState
import ru.bysoft.android.budget.features.settings.presentation.navigation.ISettingsNavigation
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    meInfo: IMeInfo,
    private val tokenRepo: ITokenRepo,
    private val navigate: ISettingsNavigation
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState(meInfo.getCurrentMeInfo()))
    val state = _state.asStateFlow()

    fun onLogoutClick() {
        tokenRepo.clearTokens()
        navigate.toAuth()
    }
}