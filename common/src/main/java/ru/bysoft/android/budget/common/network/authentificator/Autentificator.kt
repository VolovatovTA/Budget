package ru.bysoft.android.budget.common.network.authentificator

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

object EmptyTokensWhileRefreshing : Throwable()

const val tokenHeader = "Authorization"
const val tokenAdder = "Bearer "

sealed interface TokenStatus

object TokenError : TokenStatus
object TokenEmpty : TokenStatus
data class TokenSuccess(
    val tokens: AuthSuccessResponse
) : TokenStatus

interface ICommonNavigation {
    suspend fun navigateToAuth()
}

class AuthenticationInterceptorRefreshToken(
    private val refreshApi: ITokenRefreshApi,
    private val tokenRepo: ITokenStorage,
    private val navigator: ICommonNavigation
) : Interceptor {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val tokenStatus = tokenRepo.getTokens()
        .map { response ->
            response?.let { TokenSuccess(it) } ?: TokenEmpty
        }
        .stateIn(scope, SharingStarted.WhileSubscribed(), TokenEmpty)

    private var isRefreshing = false
    init {
        scope.launch {
            tokenStatus.collect {
                Log.d(TAG, "token status updated: $it")
            }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val currentState = tokenStatus.value
        when {
            currentState is TokenSuccess -> {
                val authenticationRequest = getAuthRequest(
                    request,
                    requireNotNull(currentState.tokens.accessToken)
                ) // Добавляем токен в заголовок
                val response = chain.proceed(authenticationRequest) // делаем запрос
                return when (response.code) {
                    401 -> {
                        response.close() // Закрываем старый ответ потому что иначе ругаться будет
                        refresh(request, chain) // Обновляем токен и делаем запрос заново
                    }

                    else -> {
                        response
                    }
                }
            }

            isRefreshing -> {
                return awaitRefreshingAndRequest(request, chain)
            }

            currentState is TokenError -> {
                // при старте смотрим есть ли токены, если нет то переходим на авторизацию
                runBlocking(Dispatchers.Main) { navigator.navigateToAuth() } //нужно навигироваться в мэйн потоке
                return Response.Builder()
                    .body("{\"goToAuth\": true}".toResponseBody("application/json; charset=utf-8".toMediaType()))
                    .code(200)
                    .request(request)
                    .protocol(Protocol.HTTP_1_0)
                    .message("OK")
                    .build()
            }

            currentState is TokenEmpty -> {
                // при старте токен не должен быть пустым или в ошибке. Если пустой то переходим на авторизацию
                runBlocking(Dispatchers.Main) { navigator.navigateToAuth() } //нужно навигироваться в мэйн потоке
                return Response.Builder()
                    .body("{\"goToAuth\": true}".toResponseBody("application/json; charset=utf-8".toMediaType()))
                    .code(200)
                    .request(request)
                    .protocol(Protocol.HTTP_1_0)
                    .message("OK")
                    .build()
            }

            else -> {
                return Response.Builder()
                    .body("{\"goToAuth\": error}".toResponseBody("application/json; charset=utf-8".toMediaType()))
                    .code(401)
                    .request(request)
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
        if (isRefreshing) {
            return awaitRefreshingAndRequest(originalRequest, chain)
        } else {
            isRefreshing = true
            val responseNewTokens = try {
                val answer = runBlocking { getNewTokens() }
                isRefreshing = false
                answer
            } catch (t: HttpException) {
                runBlocking { tokenRepo.clearTokens() }
                isRefreshing = false
                runBlocking(Dispatchers.Main) { navigator.navigateToAuth() } //нужно навигироваться в мэйн потоке
                return Response.Builder()
                    .body("{\"goToAuth\": error while trying to get new tokens}".toResponseBody("application/json; charset=utf-8".toMediaType()))
                    .code(401)
                    .request(originalRequest)
                    .protocol(Protocol.HTTP_1_0)
                    .message("OK")
                    .build()
            }

            if (responseNewTokens.accessToken != null && responseNewTokens.refreshToken != null) {
                runBlocking { tokenRepo.saveTokens(responseNewTokens) }
            } else {
                throw EmptyTokensWhileRefreshing
            }

            val newAuthenticationRequest =
                getAuthRequest(originalRequest, responseNewTokens.accessToken)

            return chain.proceed(newAuthenticationRequest)
        }
    }

    private suspend fun getNewTokens(): AuthSuccessResponse =
        refreshApi
            .refresh(
                TokenRefreshRequest(
                    tokenRepo.getTokens().first()?.refreshToken ?: "empty-refresh-token"
                )
            )

    private fun awaitRefreshingAndRequest(
        originalRequest: Request,
        chain: Interceptor.Chain
    ): Response {

        return runBlocking {
            val tokenSuccess =
                tokenStatus.first { tokenStatus ->
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