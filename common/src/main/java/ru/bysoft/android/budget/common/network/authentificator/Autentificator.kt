package ru.bysoft.android.budget.common.network.authentificator

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import ru.bysoft.android.budget.common.token.ITokenStorage
import ru.bysoft.android.budget.common.token.entity.AuthSuccessResponse
import ru.bysoft.android.budget.common.token.entity.TokenRefreshRequest
import ru.bysoft.android.budget.common.token.network.ITokenRefreshApi
import ru.bysoft.android.budget.common.util.TAG
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
    private val tokenRepo: ITokenStorage,
    private val navigator: ICommonNavigation
) : Interceptor {

    private fun tokenDefaultStatus() = when (val tokens = tokenRepo.getTokens()) {
        null -> null
        else -> TokenSuccess(tokens)
    }

    private val tokenStatus: MutableStateFlow<TokenStatus?> = MutableStateFlow(tokenDefaultStatus())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            tokenStatus.collect {
                Log.d(TAG, "token: $it")
            }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        when (val currentState = tokenStatus.value) {
            is TokenSuccess -> {
                println("TokenSuccess")
                val authenticationRequest = getAuthRequest(
                    request,
                    requireNotNull(currentState.tokens.accessToken)
                ) // Добавляем токен в заголовок
                val response = chain.proceed(authenticationRequest) // делаем запрос
                println(response.toString())
                return when (response.code) {
                    401 -> {
                        println("code 401")
                        response.close() // Закрываем старый ответ потому что иначе ругаться будет
                        refresh(request, chain) // Обновляем токен и делаем запрос заново
                    }
                    else -> {
                        println("success")
                        response
                    }
                }
            }
            is TokenWaiting -> {
                println("TokenWaiting")
                return awaitRefreshingAndRequest(request, chain)
            }
            is TokenError -> {
                println("TokenError")
                Log.d(TAG, "при самом старте tokens null")
                val newTokens = tokenDefaultStatus()
                return if (newTokens is TokenSuccess){
                    tokenStatus.update { newTokens }
                    intercept(chain)
                } else {
                    runBlocking(Dispatchers.Main) { navigator.navigateToAuth() } //нужно навигироваться в мэйн потоке
                    Response.Builder()
                        .body("{\"goToAuth\": true}".toResponseBody("application/json; charset=utf-8".toMediaType()))
                        .code(200)
                        .request(request)
                        .protocol(Protocol.HTTP_1_0)
                        .message("OK")
                        .build()
                }
            }
            else -> {
                println("else")
                return chain.proceed(request)
            }
        }
    }

    var conter = 0

    private fun refresh(
        originalRequest: Request,
        chain: Interceptor.Chain
    ): Response {
        println("refresh ${++conter}")
        if (tokenStatus.value is TokenWaiting) {
            return awaitRefreshingAndRequest(originalRequest, chain)
        } else {
            tokenStatus.value = TokenWaiting
            val responseNewTokens = try {
                println("responseNewTokens start")
                Log.d(TAG, "responseNewTokens start")
                val answer = runBlocking { getNewTokens() }
                println("responseNewTokens end")
                Log.d(TAG, "responseNewTokens end")
                answer
            } catch (t: HttpException) {
                println("разлогин")
                Log.d(TAG, "разлогин")
                tokenRepo.clearTokens()
                tokenStatus.update { TokenError }
                runBlocking(Dispatchers.Main) { navigator.navigateToAuth() } //нужно навигироваться в мэйн потоке
                return Response.Builder()
                    .body("{\"goToAuth\": error while trying to get new tokens}".toResponseBody("application/json; charset=utf-8".toMediaType()))
                    .code(401)
                    .request(originalRequest)
                    .protocol(Protocol.HTTP_1_0)
                    .message("OK")
                    .build()
            }
            println("responseNewTokens finish")
            Log.d(TAG, "responseNewTokens finish")

            if (responseNewTokens.accessToken != null && responseNewTokens.refreshToken != null) {
                println("responseNewTokens.accessToken")
                Log.d(TAG, "responseNewTokens responseNewTokens.accessToken")

                tokenRepo.saveTokens(responseNewTokens)
                tokenStatus.value = TokenSuccess(responseNewTokens)
            } else {
                println("responseNewTokens throw EmptyTokensWhileRefreshing")
                Log.d(TAG, "responseNewTokens throw EmptyTokensWhileRefreshing")
                throw EmptyTokensWhileRefreshing
            }

            val newAuthenticationRequest =
                getAuthRequest(originalRequest, responseNewTokens.accessToken)
            println("newAuthenticationRequest newAuthenticationRequest")
            Log.d(TAG, "newAuthenticationRequest newAuthenticationRequest")

            val response = chain.proceed(newAuthenticationRequest)
            println("response $response")
            Log.d(TAG, "response $response")

            return response
        }
    }

    private suspend fun getNewTokens(): AuthSuccessResponse =
        refreshApi
            .refresh(
                TokenRefreshRequest(
                    tokenRepo.getTokens()?.refreshToken ?: "empty-refresh-token"
                )
            )

    var counter2 = 0
    private fun awaitRefreshingAndRequest(
        originalRequest: Request,
        chain: Interceptor.Chain
    ): Response {
        println("awaitRefreshingAndRequest ${++counter2}")
        Log.d(TAG, "awaitRefreshingAndRequest ${++counter2}")

        return runBlocking {
            val tokenSuccess =
                tokenStatus.first { tokenStatus ->
                    println("tokenStatus.first $tokenStatus")
                    Log.d(TAG, "tokenStatus.first $tokenStatus")
                    tokenStatus is TokenSuccess
                } as TokenSuccess
            val newAuthenticationRequest =
                getAuthRequest(originalRequest, requireNotNull(tokenSuccess.tokens.accessToken))
            chain.proceed(newAuthenticationRequest)
        }
    }

    private fun getAuthRequest(originalRequest: Request, accessToken: String): Request =
        originalRequest
            .newBuilder()
            .addHeader(
                tokenHeader,
                tokenAdder + accessToken
            )
            .build()


}