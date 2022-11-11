package ru.bysoft.budget.auth.screen

data class AuthState(
    val email: String,
    val password: String,
    val name: String,
    val isButtonEnabled: Boolean
)