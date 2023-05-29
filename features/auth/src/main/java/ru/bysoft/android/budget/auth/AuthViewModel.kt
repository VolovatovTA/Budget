package ru.bysoft.android.budget.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.auth.data.IAuthRepository
import ru.bysoft.android.budget.auth.navigation.IAuthNavigation
import ru.bysoft.android.budget.auth.presentation.SignInResult
import ru.bysoft.android.budget.auth.presentation.entity.AuthActionType
import ru.bysoft.android.budget.auth.presentation.entity.AuthState
import ru.bysoft.android.budget.auth.presentation.mapper.getSignInData
import ru.bysoft.android.budget.auth.presentation.mapper.getSignUpData
import ru.bysoft.android.budget.common.errors.errorLogger
import ru.bysoft.android.budget.uikit.components.textfield.TextFieldState
import javax.inject.Inject

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
            isButtonEnabled = isAllComplete(password = password),
            toastText = null
        )
    }

    override fun setNewConfirmPassword(password: String) {
        state.value = state.value.copy(
            confirmPassword = TextFieldState(password, null),
            isButtonEnabled = isAllComplete(password = password),
            toastText = null
        )
    }

    override fun onCheckBoxClicked(value: Boolean) {
        state.value = state.value.copy(
            isCheckBoxChecked = value,
            isButtonEnabled = isAllComplete(isCheckBoxChecked = value),
            toastText = null
        )
    }

    override fun setNewName(name: String) {
        state.value = state.value.copy(
            name = TextFieldState(name, null),
            isButtonEnabled = isAllComplete(name = name),
            toastText = null
        )
    }

    override fun setNewEmail(email: String) {
        state.value = state.value.copy(
            email = TextFieldState(email, null),
            isButtonEnabled = isAllComplete(email = email),
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

    override fun onGoogleSignInResult(account: SignInResult) {
        viewModelScope.launch(handler) {
            repository.signInByGoogle(account.data?.idToken)
            navigate.toBottomNavigation()
        }
    }

    private fun isAllComplete(
        type: AuthActionType = state.value.type,
        email: String = state.value.email.text,
        name: String = state.value.name.text,
        password: String = state.value.password.text,
        isCheckBoxChecked: Boolean = state.value.isCheckBoxChecked
    ) =
        when (type) {
            AuthActionType.SIGN_IN -> isEmailCorrect(email) && isPasswordCorrect(password)
            AuthActionType.SIGN_UP -> isCheckBoxChecked && isEmailCorrect(email) && isPasswordCorrect(password)
                    && isNameCorrect(name)
        }


    private fun isEmailCorrect(email: String) = email.contains('@') && email.contains('.')

    private fun isPasswordCorrect(password: String) = password.count() >= 5

    private fun isNameCorrect(name: String) = name.isNotEmpty()
}