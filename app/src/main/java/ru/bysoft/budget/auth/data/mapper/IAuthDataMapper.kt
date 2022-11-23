package ru.bysoft.budget.auth.data.mapper

import ru.bysoft.budget.auth.data.entity.*
import ru.bysoft.budget.auth.data.network.entity.*

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
                SignInErrorData(errorEmailText = "Email адрес занят или введён не корректно")
            "field-password-invalid-length" ->
                SignInErrorData(errorPasswordText = "Пароль должен содержать 5 или более символов")
            "invalid-credentials" ->
                SignInErrorData(errorToastText = "Неверные почта или пароль", errorPasswordText = "", errorEmailText = "")
            "invalid-input" -> SignInErrorData()
            else -> throw Throwable()
        }
    } else {
        null
    }

fun SignUpErrorResponse.mapToErrorData() =
    if (this.slug != null) {
        when (slug) {
            "field-email-invalid" ->
                SignUpErrorData(errorEmailText = "Email адрес занят или введён не корректно")
            "field-email-required" ->
                SignUpErrorData(errorEmailText = "Нужен ваш email")
            "field-password-required" ->
                SignUpErrorData(errorPasswordText = "Без пароля не получиться зарегестрироваться")
            "field-name-required" ->
                SignUpErrorData(errorNameText = "Без вашего имени регистрация невозможна")
            "field-name-invalid-length" ->
                SignUpErrorData(errorNameText = "Имя должно содержать хотя бы один символ")
            "field-password-invalid-length" ->
                SignUpErrorData(errorPasswordText = "Пароль должен содержать 5 или более символов")
            "invalid-credentials" ->
                SignUpErrorData(errorToastText = "Неверные почта или пароль", errorPasswordText = "", errorEmailText = "")
            "internal-server-error" ->
                SignUpErrorData(errorToastText = "Произошла ошибка сервера. Мы о ней знаем и скоро исправим")
            "invalid-input" -> SignUpErrorData()
            else -> throw Throwable()
        }
    } else {
        null
    }