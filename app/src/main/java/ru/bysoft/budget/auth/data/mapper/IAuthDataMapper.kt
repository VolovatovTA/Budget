package ru.bysoft.budget.auth.data.mapper

import ru.bysoft.budget.auth.data.entity.SignInData
import ru.bysoft.budget.auth.data.entity.SignUpData
import ru.bysoft.budget.auth.data.network.entity.SignInRequest
import ru.bysoft.budget.auth.data.network.entity.SignUpRequest
import javax.inject.Inject

interface IAuthDataMapper {
    fun getSignInRequest(signInData: SignInData): SignInRequest
    fun getSignUpRequest(signUpData: SignUpData): SignUpRequest
}

class AuthDataMapper @Inject constructor() : IAuthDataMapper {

    override fun getSignInRequest(signInData: SignInData) =
        SignInRequest(
            email = signInData.email,
            password = signInData.password
        )

    override fun getSignUpRequest(signUpData: SignUpData)=
        SignUpRequest(
            email = signUpData.email,
            password = signUpData.password,
            name = signUpData.name
        )


}