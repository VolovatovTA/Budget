package ru.bysoft.android.budget.auth

import ru.bysoft.android.budget.common.errors.IErrorLogger
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.auth.data.IAuthRepository
import ru.bysoft.android.budget.auth.navigation.IAuthNavigation
import ru.bysoft.android.budget.auth.presentation.SignInResult
import ru.bysoft.android.budget.auth.presentation.entity.AuthActionType
import ru.bysoft.android.budget.auth.presentation.entity.AuthState
import ru.bysoft.android.budget.auth.presentation.mapper.getSignInData
import ru.bysoft.android.budget.auth.presentation.mapper.getSignUpData
import ru.bysoft.android.budget.common.errors.handler
import ru.bysoft.android.budget.uikit.components.textfield.TextFieldState

interface IAuthViewModel {
    val state: StateFlow<AuthState>
    fun setNewPassword(password: String)
    fun setNewConfirmPassword(password: String)
    fun onCheckBoxClicked(value: Boolean)
    fun setNewName(name: String)
    fun setNewEmail(email: String)
    fun onButtonClick(action: AuthActionType)
    fun switchAuthType(newType: AuthActionType)
    fun onGoogleSignInResult(account: SignInResult)
    fun setLoading(b: Boolean)
}

class AuthViewModel(
    private val errorLogger: IErrorLogger,
    private val repository: IAuthRepository,
    private val navigate: IAuthNavigation
) : ViewModel(), IAuthViewModel {

    private val handler = errorLogger.handler {
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
        state.update {
            it.copy(
                password =
                    TextFieldState(
                        password,
                        if (it.type == AuthActionType.SIGN_UP && password != state.value.confirmPassword.text) R.string.passwords_not_match else null
                    ),
                confirmPassword = it.confirmPassword.copy(
                    errorText = if (it.type == AuthActionType.SIGN_UP && password != state.value.confirmPassword.text) R.string.passwords_not_match else null
                ), isButtonEnabled = isAllComplete(password = password),
                toastText = null
            )
        }
    }

    override fun setNewConfirmPassword(password: String) {
        state.update {
            it.copy(
                confirmPassword =
                    TextFieldState(
                        password,
                        if (password != state.value.password.text) R.string.passwords_not_match else null
                    ),
                password = it.password.copy(
                    errorText = if (it.type == AuthActionType.SIGN_UP && password != state.value.password.text) R.string.passwords_not_match else null
                ),
                isButtonEnabled = isAllComplete(confirmPassword = password),
                toastText = null
            )
        }
    }

    override fun onCheckBoxClicked(value: Boolean) {
        state.update {
            it.copy(
                isCheckBoxChecked = value,
                isButtonEnabled = isAllComplete(isCheckBoxChecked = value),
                toastText = null
            )
        }
        state.update {
            it.copy(
                isGoogleButtonEnabled = isAllForGoogleComplete(it)
            )
        }
    }

    private fun isAllForGoogleComplete(state: AuthState): Boolean {
        return if (state.type == AuthActionType.SIGN_IN) true
        else state.isCheckBoxChecked
    }

    override fun setNewName(name: String) {
        state.update {
            it.copy(
                name = TextFieldState(name, null),
                isButtonEnabled = isAllComplete(name = name),
                toastText = null
            )
        }
    }

    override fun setNewEmail(email: String) {
        state.update {
            it.copy(
                email = TextFieldState(email, null),
                isButtonEnabled = isAllComplete(email = email),
                toastText = null
            )
        }
    }

    override fun onButtonClick(action: AuthActionType) {
        state.update {
            it.copy(
                isLoading = true,
                toastText = null
            )
        }
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
        state.update {
            it.copy(
                type = newType,
                toastText = null,
                password = it.password.copy(errorText = if (newType == AuthActionType.SIGN_IN) null else it.password.errorText),
                isButtonEnabled = isAllComplete(type = newType)
            )
        }
        state.update {
            it.copy(
                isGoogleButtonEnabled = isAllForGoogleComplete(it)
            )
        }
    }

    override fun onGoogleSignInResult(account: SignInResult) {
        if (account.token != null) {
            viewModelScope.launch(handler) {
                repository.signInByGoogle(account.token)
                state.update { it.copy(isLoading = false) }
                navigate.toBottomNavigation()
            }
        } else {
            state.update { it.copy(isLoading = false) }
        }

    }

    override fun setLoading(b: Boolean) {
        state.update { it.copy(isLoading = b) }
    }

    private fun isAllComplete(
        type: AuthActionType = state.value.type,
        email: String = state.value.email.text,
        name: String = state.value.name.text,
        password: String = state.value.password.text,
        confirmPassword: String = state.value.confirmPassword.text,
        isCheckBoxChecked: Boolean = state.value.isCheckBoxChecked
    ) =
        when (type) {
            AuthActionType.SIGN_IN -> isEmailCorrect(email) && isPasswordCorrect(password)
            AuthActionType.SIGN_UP -> isCheckBoxChecked && isEmailCorrect(email) && isPasswordCorrect(
                password
            )
                    && isNameCorrect(name) && password == confirmPassword
        }


    private fun isEmailCorrect(email: String) = email.contains('@') && email.contains('.')

    private fun isPasswordCorrect(password: String) = password.count() >= 6

    private fun isNameCorrect(name: String) = name.isNotEmpty()
}