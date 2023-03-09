package ru.bysoft.android.budget.common.network.authentificator

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import ru.bysoft.android.budget.common.token.ITokenRepo
import ru.bysoft.android.budget.common.token.entity.AuthSuccessResponse
import ru.bysoft.android.budget.common.token.entity.TokenRefreshRequest
import ru.bysoft.android.budget.common.token.network.ITokenRefreshApi
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

object EmptyTokensWhileRefreshing : Throwable()

const val tokenHeader = "Authorization"
const val tokenAdder = "Bearer "

sealed interface TokenStatus

object TokenWaiting : TokenStatus
object TokenError : TokenStatus
data class TokenSuccess(
    val tokens: AuthSuccessResponse
) : TokenStatus

interface ICommonNavigation {
    fun navigateToAuth()
}

@Singleton
class AuthenticationInterceptorRefreshToken @Inject constructor(
    private val refreshApi: ITokenRefreshApi,
    private val tokenRepo: ITokenRepo,
    private val navigator: ICommonNavigation
) : Interceptor {

    private val tokenDefaultStatus = when(val tokens = tokenRepo.getTokens()){
        null -> TokenError
        else -> TokenSuccess(tokens)
    }

    private var tokenStatus: MutableStateFlow<TokenStatus> = MutableStateFlow(tokenDefaultStatus)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        when(val currentState = tokenStatus.value){
            is TokenSuccess -> {
                val authenticationRequest = getAuthRequest(request, requireNotNull(currentState.tokens.accessToken)) // Добавляем токен в заголовок
                val response = chain.proceed(authenticationRequest) // делаем запрос
                return when (response.code) {
                    401 -> {
                        response.close() // Закрываем старый ответ потому что иначе ругаться будет
                        refresh(request, chain) // Обновляем токен и делаем запрос заново
                    }
                    else -> response
                }
            }
            is TokenWaiting -> {
                return awaitRefreshingAndRequest(request, chain)
            }
            is TokenError -> {
                runBlocking(Dispatchers.Main) { navigator.navigateToAuth() } //нужно навигироваться в мэйн потоке
                return Response.Builder()
                    .body("{\"goToAuth\": true}".toResponseBody("application/json; charset=utf-8".toMediaType()))
                    .code(200)
                    .request(getNewTokens().request())
                    .protocol(Protocol.HTTP_1_0)
                    .message("OK")
                    .build()
            }
        }
    }

    private fun refresh(
        originalRequest: Request,
        chain: Interceptor.Chain
    ): Response {
        if (tokenStatus.value is TokenWaiting) {
            return awaitRefreshingAndRequest(originalRequest, chain)
        } else {
            tokenStatus.value = TokenWaiting
            val responseNewTokens = getNewTokens().execute()

            return when {
                responseNewTokens.body() == null || responseNewTokens.code() != 200 -> {
                    tokenRepo.clearTokens()
                    tokenStatus.value = TokenError
                    runBlocking(Dispatchers.Main) { navigator.navigateToAuth() } //нужно навигироваться в мэйн потоке
                    Response.Builder()
                        .body("{\"goToAuth\": true}".toResponseBody("application/json; charset=utf-8".toMediaType()))
                        .code(200)
                        .request(getNewTokens().request())
                        .protocol(Protocol.HTTP_1_0)
                        .message("OK")
                        .build()
                }
                else -> {
                    val tokens = responseNewTokens.body()
                    if (tokens?.accessToken != null && tokens.refreshToken != null) {
                        tokenRepo.saveTokens(tokens)
                        tokenStatus.value = TokenSuccess(tokens)
                    } else {
                        throw EmptyTokensWhileRefreshing
                    }

                    val newAuthenticationRequest = getAuthRequest(originalRequest, tokens.accessToken)
                    chain.proceed(newAuthenticationRequest)
                }
            }
        }
    }

    private fun getNewTokens() =
        refreshApi
            .refresh(
                TokenRefreshRequest(
                    tokenRepo.getTokens()?.refreshToken ?: "empty-refresh-token"
                )
            )

    private fun awaitRefreshingAndRequest(
        originalRequest: Request,
        chain: Interceptor.Chain
    ): Response {
        return runBlocking {
            val tokenSuccess = tokenStatus.first { tokenStatus -> tokenStatus is TokenSuccess } as TokenSuccess
            val newAuthenticationRequest = getAuthRequest(originalRequest, requireNotNull(tokenSuccess.tokens.accessToken))
            chain.proceed(newAuthenticationRequest)
        }
    }

    private fun getAuthRequest(originalRequest: Request, accessToken: String) = originalRequest
        .newBuilder()
        .addHeader(
            tokenHeader,
            tokenAdder + accessToken
        )
        .build()


}