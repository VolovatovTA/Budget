package ru.bysoft.budget.auth.data

import android.util.Log
import ru.bysoft.budget.auth.data.entity.SignInData
import ru.bysoft.budget.auth.data.entity.SignUpData
import ru.bysoft.budget.auth.data.mapper.IAuthDataMapper
import ru.bysoft.budget.auth.data.network.IAuthApi
import ru.bysoft.budget.common.token.ITokenRepo
import javax.inject.Inject

const val TAG = "Timofey"

interface IAuthRepository {
    suspend fun signIn(signInData: SignInData)
    suspend fun signUp(signUpData: SignUpData)
}

class AuthRepository @Inject constructor(
    private val mapper: IAuthDataMapper,
    private val api: IAuthApi,
    private val tokenRepo: ITokenRepo
) : IAuthRepository {

    override suspend fun signIn(signInData: SignInData) {
        val authResponse = api.signIn(mapper.getSignInRequest(signInData))
        Log.d(TAG, "signIn: $authResponse")
        tokenRepo.saveToken(authResponse)
    }

    override suspend fun signUp(signUpData: SignUpData) {
        Log.d(TAG, "signUp: ")
        val authResponse = api.signUp(mapper.getSignUpRequest(signUpData))
        Log.d(TAG, "signUn: $authResponse")
        tokenRepo.saveToken(authResponse)
    }


}