package ru.bysoft.android.budget.auth.data.mapper

import ru.budget.android.api.data.source.network.entity.auth.SignInRequest
import ru.budget.android.api.data.source.network.entity.auth.SignUpRequest
import ru.bysoft.android.budget.auth.data.entity.SignInData
import ru.bysoft.android.budget.auth.data.entity.SignInErrorData
import ru.bysoft.android.budget.auth.data.entity.SignUpData
import ru.bysoft.android.budget.auth.data.entity.SignUpErrorData
import ru.bysoft.android.budget.auth.R
import ru.bysoft.android.budget.common.token.entity.SignInErrorResponse
import ru.bysoft.android.budget.common.token.entity.SignUpErrorResponse

fun SignInData.mapToSignInRequest(): SignInRequest = SignInRequest(
    email = this.email,
    password = this.password
)

fun SignUpData.mapToSignUpRequest(): SignUpRequest = SignUpRequest(
    email = this.email,
    password = this.password,
    name = this.name
)

fun SignInErrorResponse.mapToErrorData() =
    if (this.slug != null) {
        when (slug) {
            "field-email-invalid" ->
                SignInErrorData(errorEmailText = R.string.field_email_invalid)
            "field-password-invalid-length" ->
                SignInErrorData(errorPasswordText = R.string.field_password_invalid_length)
            "invalid-credentials" ->
                SignInErrorData(errorToastText = R.string.invalid_credentials, errorPasswordText = R.string.empty_text, errorEmailText = R.string.empty_text)
            "invalid-input" -> SignInErrorData()
            "error_certificate" -> SignInErrorData(errorToastText = R.string.certificate_error)
            else -> SignInErrorData()
        }
    } else {
        null
    }

fun SignUpErrorResponse.mapToErrorData() =
    if (this.slug != null) {
        when (slug) {
            "field-email-invalid" ->
                SignUpErrorData(errorEmailText = R.string.field_email_invalid)
            "field-email-required" ->
                SignUpErrorData(errorEmailText = R.string.field_email_required)
            "field-password-required" ->
                SignUpErrorData(errorPasswordText = R.string.field_password_required)
            "field-name-required" ->
                SignUpErrorData(errorNameText = R.string.field_name_required)
            "field-name-invalid-length" ->
                SignUpErrorData(errorNameText = R.string.field_name_invalid_length)
            "field-password-invalid-length" ->
                SignUpErrorData(errorPasswordText = R.string.field_password_invalid_length)
            "invalid-credentials" ->
                SignUpErrorData(errorToastText = R.string.invalid_credentials, errorPasswordText = R.string.empty_text, errorEmailText = R.string.empty_text)
            "internal-server-error" ->
                SignUpErrorData(errorToastText = R.string.internal_server_error)
            "invalid-input" -> SignUpErrorData()
            "error_certificate" -> SignUpErrorData(errorToastText = R.string.certificate_error)
            else -> throw Throwable()
        }
    } else {
        null
    }