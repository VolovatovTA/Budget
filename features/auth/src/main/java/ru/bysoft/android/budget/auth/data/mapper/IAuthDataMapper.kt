package ru.bysoft.android.budget.auth.data.mapper

import ru.budget.android.api.data.source.network.entity.auth.SignInRequest
import ru.budget.android.api.data.source.network.entity.auth.SignUpRequest
import ru.bysoft.android.budget.auth.R
import ru.bysoft.android.budget.auth.data.entity.SignInData
import ru.bysoft.android.budget.auth.data.entity.SignUpData
import ru.bysoft.android.budget.auth.data.entity.SignErrorData
import ru.bysoft.android.budget.common.token.entity.SignErrorResponse

fun SignInData.mapToSignInRequest(): SignInRequest = SignInRequest(
    email = this.email,
    password = this.password
)

fun SignUpData.mapToSignUpRequest(): SignUpRequest = SignUpRequest(
    email = this.email,
    password = this.password,
    name = this.name
)


fun SignErrorResponse.mapToErrorData(): SignErrorData {
    if (error == "Invalid credentials")
        return SignErrorData(
            errorPasswordText = R.string.field_password_invalid_length,
            errorEmailText = R.string.field_email_invalid,
            errorToastText = R.string.invalid_credentials
        )


    return SignErrorData(
        errorPasswordText = getPasswordTextId(errors?.password),
        errorEmailText = getEmailTextId(errors?.email),
        errorNameText = getNameTextId(errors?.name)
    )
}


fun getEmailTextId(emailError: List<String>?): Int? {
    return emailError?.firstNotNullOfOrNull {
            when (it) {
                "The email field is required.", "The email must be a valid email address." -> R.string.field_email_invalid
                else -> null
            }
        }
}

fun getPasswordTextId(passwordError: List<String>?): Int? {
    return passwordError?.firstNotNullOfOrNull {
        when (it) {
            "The password field is required.", "The password must be at least 6 characters." -> R.string.field_password_invalid_length
            else -> null
        }
    }
}

fun getNameTextId(nameError: List<String>?): Int? {
    return nameError?.firstNotNullOfOrNull {
        when (it) {
            "The name must be a string." -> R.string.field_name_required
            "The name must be at least 3 characters." -> R.string.field_name_invalid_length
            else -> null
        }
    }
}