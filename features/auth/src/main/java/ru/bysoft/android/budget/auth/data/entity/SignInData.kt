package ru.bysoft.android.budget.auth.data.entity

data class SignInData(
    val email: String,
    val password: String
)

data class SignUpData(
    val name: String,
    val email: String,
    val password: String
)

data class SignErrorData(
    val errorNameText: Int? = null,
    val errorEmailText: Int? = null,
    val errorPasswordText: Int? = null,
    val errorToastText: Int? = null
)