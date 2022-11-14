package ru.bysoft.budget.auth.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.bysoft.budget.auth.data.IAuthRepository
import ru.bysoft.budget.auth.presentation.entity.AuthState
import javax.inject.Inject

interface IAuthViewModel {
    val state: StateFlow<AuthState>
    fun setNewPassword(newText: String)
    fun setNewName(newText: String)
    fun setNewEmail(newText: String)
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: IAuthRepository
) : ViewModel(), IAuthViewModel {
    override val state: MutableStateFlow<AuthState> =
        MutableStateFlow(AuthState("", "", "", false))

    override fun setNewPassword(newText: String) {
        state.value = state.value.copy(
            password = newText
        )
    }

    override fun setNewName(newText: String) {
        state.value = state.value.copy(
            name = newText
        )
    }

    override fun setNewEmail(newText: String) {
        state.value = state.value.copy(
            email = newText
        )
    }

    private fun isAllComplete(state: AuthState) =
        isEmailCorrect(state.email) && isPasswordCorrect(state.password)

    private fun isEmailCorrect(email: String) = email.contains('@')&& email.contains('.')

    private fun isPasswordCorrect(password: String) = password.length > 5
}