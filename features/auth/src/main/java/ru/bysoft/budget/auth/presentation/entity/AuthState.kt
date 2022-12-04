package ru.bysoft.budget.auth.presentation.entity

import ru.bysoft.budget.uikit.components.textfield.TextFieldState

data class AuthState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val name: TextFieldState = TextFieldState(),
    val isButtonEnabled: Boolean = false,
    val type: AuthActionType,
    val isLoading: Boolean,
    val toastText: String?,
    val navAction: NavAction? = null
)

enum class AuthActionType {
    SIGN_IN, SIGN_UP
}

enum class NavAction {
    BOTTOM_NAVIGATION
}