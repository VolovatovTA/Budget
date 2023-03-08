package ru.bysoft.budget.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.bysoft.budget.auth.data.IAuthRepository
import ru.bysoft.budget.auth.navigation.IAuthNavigation
import ru.bysoft.budget.auth.presentation.entity.AuthActionType
import ru.bysoft.budget.auth.presentation.entity.AuthState
import ru.bysoft.budget.auth.presentation.mapper.getSignInData
import ru.bysoft.budget.auth.presentation.mapper.getSignUpData
import ru.bysoft.budget.common.errors.errorLogger
import ru.bysoft.budget.uikit.components.textfield.TextFieldState
import javax.inject.Inject

interface IAuthViewModel {
    val state: StateFlow<AuthState>
    fun setNewPassword(password: String)
    fun setNewName(name: String)
    fun setNewEmail(email: String)
    fun onButtonClick(action: AuthActionType)
    fun switchAuthType(newType: AuthActionType)
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: IAuthRepository,
    private val navigate: IAuthNavigation
) : ViewModel(), IAuthViewModel {

    private val handler = CoroutineExceptionHandler { _, t ->
        errorLogger.logError(t)
        state.value = state.value.copy(
            toastText = R.string.unexpected_error,
            isLoading = false
        )
    }
    override val state: MutableStateFlow<AuthState> =
        MutableStateFlow(
            AuthState(
                type = AuthActionType.SIGN_IN,
                isLoading = false,
                toastText = null
            )
        )

    override fun setNewPassword(password: String) {
        state.value = state.value.copy(
            password = TextFieldState(password, null),
            isButtonEnabled = isAllComplete(state.value),
            toastText = null
        )
    }

    override fun setNewName(name: String) {
        state.value = state.value.copy(
            name = TextFieldState(name, null),
            isButtonEnabled = isAllComplete(state.value),
            toastText = null
        )
    }

    override fun setNewEmail(email: String) {
        state.value = state.value.copy(
            email = TextFieldState(email, null),
            isButtonEnabled = isAllComplete(state.value),
            toastText = null
        )
    }

    override fun onButtonClick(action: AuthActionType) {
        state.value = state.value.copy(
            isLoading = true,
            toastText = null
        )
        when (action) {
            AuthActionType.SIGN_IN -> {
                viewModelScope.launch(handler) {
                    val errorData = repository.signIn(state.value.getSignInData())
                    state.value = state.value.copy(
                        isLoading = false,
                        toastText = null
                    )

                    if (errorData == null) {
                        navigate.toBottomNavigation()
                    } else {
                        state.value = state.value.copy(
                            email = state.value.email.copy(errorText = errorData.errorEmailText),
                            password = state.value.password.copy(errorText = errorData.errorPasswordText),
                            toastText = errorData.errorToastText
                        )
                    }
                }
            }
            AuthActionType.SIGN_UP -> {
                viewModelScope.launch(handler) {
                    val errorData = repository.signUp(state.value.getSignUpData())
                    state.value = state.value.copy(
                        isLoading = false,
                        toastText = null
                    )
                    if (errorData == null) {
                        navigate.toBottomNavigation()
                    } else {
                        state.value = state.value.copy(
                            email = state.value.email.copy(errorText = errorData.errorEmailText),
                            password = state.value.password.copy(errorText = errorData.errorPasswordText),
                            name = state.value.name.copy(errorText = errorData.errorNameText),
                            toastText = errorData.errorToastText
                        )
                    }
                }
            }
        }
    }

    override fun switchAuthType(newType: AuthActionType) {
        state.value = state.value.copy(
            type = newType,
            toastText = null
        )
    }

    private fun isAllComplete(state: AuthState) =
        isEmailCorrect(state.email.text) && isPasswordCorrect(state.password.text) && isNameCorrect(
            state.name.text
        )

    private fun isEmailCorrect(email: String) = email.contains('@') && email.contains('.') || true

    private fun isPasswordCorrect(password: String) = password.length >= 5 || true

    private fun isNameCorrect(name: String) = name.isNotEmpty() || true
}