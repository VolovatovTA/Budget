package ru.bysoft.budget.auth.presentation.mapper

import ru.bysoft.budget.auth.data.entity.SignInData
import ru.bysoft.budget.auth.data.entity.SignUpData
import ru.bysoft.budget.auth.presentation.entity.AuthState

fun AuthState.getSignInData() =
    SignInData(
        email = email.text,
        password = password.text
    )

fun AuthState.getSignUpData()=
    SignUpData(
        email = email.text,
        password = password.text,
        name = name.text
    )