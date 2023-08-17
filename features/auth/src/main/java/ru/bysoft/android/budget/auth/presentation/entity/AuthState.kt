package ru.bysoft.android.budget.auth.presentation.entity

import ru.bysoft.android.budget.uikit.components.textfield.TextFieldState

data class AuthState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val confirmPassword: TextFieldState = TextFieldState(),
    val name: TextFieldState = TextFieldState(),
    val isButtonEnabled: Boolean = false,
    val isGoogleButtonEnabled: Boolean = true,
    val type: AuthActionType,
    val isLoading: Boolean,
    val toastText: Int?,
    val isCheckBoxChecked: Boolean = false
)

enum class AuthActionType {
    SIGN_IN, SIGN_UP
}