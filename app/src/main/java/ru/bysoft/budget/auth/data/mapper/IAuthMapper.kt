package ru.bysoft.budget.auth.data.mapper

import ru.bysoft.budget.auth.presentation.entity.AuthState
import javax.inject.Inject

interface IAuthMapper {
    fun getSignInRequest(authState: AuthState)
    fun getSignUpRequest(authState: AuthState)

}

class AuthMapper @Inject constructor() : IAuthMapper {

    override fun getSignInRequest(authState: AuthState) {
        TODO("Not yet implemented")
    }

    override fun getSignUpRequest(authState: AuthState) {
        TODO("Not yet implemented")
    }


}