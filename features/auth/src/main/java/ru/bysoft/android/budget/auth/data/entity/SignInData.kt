package ru.bysoft.android.budget.auth.data.entity

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
    val errorEmailText: Int? = null,
    val errorPasswordText: Int? = null,
    val errorToastText: Int? = null
) : SignInResult

data class SignUpErrorData(
    val errorNameText: Int? = null,
    val errorEmailText: Int? = null,
    val errorPasswordText: Int? = null,
    val errorToastText: Int? = null
) : SignUpResult