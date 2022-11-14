package ru.bysoft.budget.auth.presentation.entity

data class AuthState(
    val email: TextFieldState,
    val password: TextFieldState,
    val name: TextFieldState,
    val isButtonEnabled: Boolean
)