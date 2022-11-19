package ru.bysoft.budget.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.bysoft.budget.auth.data.IAuthRepository
import ru.bysoft.budget.auth.data.TAG
import ru.bysoft.budget.auth.presentation.entity.AuthActionType
import ru.bysoft.budget.auth.presentation.entity.AuthState
import ru.bysoft.budget.auth.presentation.mapper.getSignInData
import ru.bysoft.budget.auth.presentation.mapper.getSignUpData
import ru.bysoft.budget.common.navigation.BottomNavigation
import ru.bysoft.budget.uikit.components.textfield.TextFieldState
import javax.inject.Inject

interface IAuthViewModel {
    val state: StateFlow<AuthState>
    fun init(controller: NavHostController)
    fun setNewPassword(password: String)
    fun setNewName(name: String)
    fun setNewEmail(email: String)
    fun onButtonClick(action: AuthActionType)
    fun switchAuthType(newType: AuthActionType)
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: IAuthRepository,
) : ViewModel(), IAuthViewModel {

    private lateinit var controller: NavHostController

    private val handler = CoroutineExceptionHandler { _, t ->
        t.printStackTrace()
    }

    override val state: MutableStateFlow<AuthState> =
        MutableStateFlow(AuthState(type = AuthActionType.SIGN_IN))

    override fun init(controller: NavHostController) {
        this.controller = controller
    }

    override fun setNewPassword(password: String) {
        state.value = state.value.copy(
            password = TextFieldState(password, !isPasswordCorrect(password)),
            isButtonEnabled = isAllComplete(state.value)
        )
    }

    override fun setNewName(name: String) {
        state.value = state.value.copy(
            name = TextFieldState(name, name == ""),
            isButtonEnabled = isAllComplete(state.value)
        )
    }

    override fun setNewEmail(email: String) {
        state.value = state.value.copy(
            email = TextFieldState(email, !isEmailCorrect(email)),
            isButtonEnabled = isAllComplete(state.value)
        )
    }

    override fun onButtonClick(action: AuthActionType) {
        when (action) {
            AuthActionType.SIGN_IN -> {
                viewModelScope.launch(handler) {
                    repository.signIn(state.value.getSignInData())
                    Log.d(TAG, "afterSignIn: $controller")
                    controller.navigate(BottomNavigation.route)

                }
            }
            AuthActionType.SIGN_UP -> {
                viewModelScope.launch(handler) {
                    repository.signUp(state.value.getSignUpData())
                    controller.navigate(BottomNavigation.route)
                }
            }
        }
    }

    override fun switchAuthType(newType: AuthActionType) {
        state.value = state.value.copy(type = newType)
    }

    private fun isAllComplete(state: AuthState) =
        isEmailCorrect(state.email.text) && isPasswordCorrect(state.password.text)

    private fun isEmailCorrect(email: String) = email.contains('@') && email.contains('.')

    private fun isPasswordCorrect(password: String) = password.length > 5
}