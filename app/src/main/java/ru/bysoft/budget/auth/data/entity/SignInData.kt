package ru.bysoft.budget.auth.data.entity

sealed interface SignInResult
sealed interface SignUpResult

data class SignInData(
    val email: String,
    val password: String
)

data class SignUpData(
    val name: String,
    val email: String,
    val password: String
)

data class SignInErrorData(
    val errorEmailText: String? = null,
    val errorPasswordText: String? = null,
    val errorToastText: String? = null
) : SignInResult

data class SignUpErrorData(
    val errorNameText: String? = null,
    val errorEmailText: String? = null,
    val errorPasswordText: String? = null,
    val errorToastText: String? = null
) : SignUpResult