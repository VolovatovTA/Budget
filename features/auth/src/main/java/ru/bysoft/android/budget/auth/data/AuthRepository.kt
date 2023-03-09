package ru.bysoft.android.budget.auth.data

import retrofit2.HttpException
import ru.bysoft.android.budget.auth.data.entity.SignInData
import ru.bysoft.android.budget.auth.data.entity.SignInErrorData
import ru.bysoft.android.budget.auth.data.entity.SignUpData
import ru.bysoft.android.budget.auth.data.entity.SignUpErrorData
import ru.bysoft.android.budget.auth.data.mapper.mapToErrorData
import ru.bysoft.android.budget.auth.data.mapper.mapToSignInRequest
import ru.bysoft.android.budget.auth.data.mapper.mapToSignUpRequest
import ru.bysoft.android.budget.auth.data.network.IAuthApi
import ru.bysoft.android.budget.common.token.ITokenRepo
import ru.bysoft.android.budget.common.token.entity.AuthResponse
import ru.bysoft.android.budget.common.token.entity.AuthSuccessResponse
import ru.bysoft.android.budget.common.token.entity.SignInErrorResponse
import ru.bysoft.android.budget.common.token.entity.SignUpErrorResponse
import ru.bysoft.android.budget.common.util.restore
import javax.inject.Inject

interface IAuthRepository {
    suspend fun signIn(signInData: SignInData): SignInErrorData?
    suspend fun signUp(signUpData: SignUpData): SignUpErrorData?
}

class AuthRepository @Inject constructor(
    private val api: IAuthApi,
    private val tokenRepo: ITokenRepo
) : IAuthRepository {

    override suspend fun signIn(signInData: SignInData): SignInErrorData? {
        val authResponse: AuthResponse = try {
            api.signIn(signInData.mapToSignInRequest())
        } catch (e: HttpException) {
            val responseJson = e.response()?.errorBody()?.string()
            responseJson?.restore<SignInErrorResponse>() ?: throw EmptySlugMessage(responseJson)
        }

        return when (authResponse) {
            is SignInErrorResponse -> {
                authResponse.mapToErrorData()
            }
            is AuthSuccessResponse -> {
                tokenRepo.saveTokens(authResponse)
                null
            }
            else -> throw Throwable()
        }
    }

    override suspend fun signUp(signUpData: SignUpData): SignUpErrorData? {
        val authResponse: AuthResponse = try {
            api.signUp(signUpData.mapToSignUpRequest())
        } catch (e: HttpException) {
            val responseJson = e.response()?.errorBody()?.string()
            responseJson?.restore<SignUpErrorResponse>() ?: throw EmptySlugMessage(responseJson)
        }

        return when (authResponse) {
            is SignUpErrorResponse -> {
                authResponse.mapToErrorData()
            }
            is AuthSuccessResponse -> {
                tokenRepo.saveTokens(authResponse)
                null
            }
            else -> throw Throwable()
        }
    }

}

class EmptySlugMessage(json: String?): Throwable(json)