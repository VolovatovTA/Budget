package ru.bysoft.android.budget.auth.data

import ru.budget.android.api.data.source.network.IAuthApi
import ru.budget.android.api.data.source.network.entity.auth.SignInGoogleRequest
import ru.bysoft.android.budget.auth.data.entity.SignInData
import ru.bysoft.android.budget.auth.data.entity.SignUpData
import ru.bysoft.android.budget.auth.data.entity.SignErrorData
import ru.bysoft.android.budget.auth.data.mapper.mapToErrorData
import ru.bysoft.android.budget.auth.data.mapper.mapToSignInRequest
import ru.bysoft.android.budget.auth.data.mapper.mapToSignUpRequest
import ru.bysoft.android.budget.common.network.entity.ifHttpErrorGetErrorBody
import ru.bysoft.android.budget.common.token.ITokenStorage
import ru.bysoft.android.budget.common.token.entity.AuthResponse
import ru.bysoft.android.budget.common.token.entity.AuthSuccessResponse
import ru.bysoft.android.budget.common.token.entity.SignErrorResponse
import ru.bysoft.android.budget.common.util.restore
import javax.net.ssl.SSLPeerUnverifiedException

interface IAuthRepository {
    suspend fun signIn(signInData: SignInData): SignErrorData?
    suspend fun signUp(signUpData: SignUpData): SignErrorData?
    suspend fun signInByGoogle(idToken: String?)
}

class AuthRepository(
    private val api: IAuthApi,
    private val tokenRepo: ITokenStorage,
) : IAuthRepository {

    override suspend fun signIn(signInData: SignInData): SignErrorData? {
        val authResponse: AuthResponse = try {
            api.signIn(signInData.mapToSignInRequest())
        } catch (e: Throwable) {

            val errorJson = e.ifHttpErrorGetErrorBody()

            when {
                errorJson != null -> {
                    errorJson.restore<SignErrorResponse>()
                        ?: throw EmptySlugMessage(errorJson)
                }

                e is SSLPeerUnverifiedException -> SignErrorResponse(
                    "error_certificate",
                )

                else -> SignErrorResponse(
                    "unknown_error",
                )
            }
        }

        return when (authResponse) {
            is SignErrorResponse -> {
                authResponse.mapToErrorData()
            }

            is AuthSuccessResponse -> {
                tokenRepo.saveTokens(authResponse)
                null
            }

            else -> throw Throwable()
        }
    }

    override suspend fun signUp(signUpData: SignUpData): SignErrorData? {
        val authResponse: AuthResponse = try {
            api.signUp(signUpData.mapToSignUpRequest())
        } catch (e: Throwable) {

            val errorJson = e.ifHttpErrorGetErrorBody()

            when {
                errorJson != null -> {
                    errorJson.restore<SignErrorResponse>() ?: throw EmptySlugMessage(errorJson)
                }

                e is SSLPeerUnverifiedException -> SignErrorResponse(
                    "error_certificate"
                )

                else -> SignErrorResponse(
                    "unknown_error"
                )
            }
        }

        return when (authResponse) {
            is SignErrorResponse -> {
                authResponse.mapToErrorData()
            }

            is AuthSuccessResponse -> {
                tokenRepo.saveTokens(authResponse)
                null
            }

            else -> throw Throwable()
        }
    }

    override suspend fun signInByGoogle(idToken: String?) {
        val authResponse = api.signInByGoogle(SignInGoogleRequest(idToken))
        tokenRepo.saveTokens(authResponse)

    }

}

class EmptySlugMessage(json: String?) : Throwable(json)