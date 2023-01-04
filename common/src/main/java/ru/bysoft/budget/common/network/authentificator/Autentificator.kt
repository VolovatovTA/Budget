package ru.bysoft.budget.common.network.authentificator

import android.util.Log
import androidx.navigation.NavHostController
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import ru.bysoft.budget.common.token.ITokenRepo
import ru.bysoft.budget.common.token.entity.AuthSuccessResponse
import ru.bysoft.budget.common.token.entity.TokenRefreshRequest
import ru.bysoft.budget.common.token.network.ITokenRefreshApi
import ru.bysoft.budget.common.util.TAG
import ru.bysoft.budget.common.util.onNull
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

object ThrowableWhenTryingRefreshToken : Throwable()
object EmptyTokensWhileRefreshing : Throwable()
class UnknownSlugMessage(slug: String?) : Throwable(slug)

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

    private var tokenStatus: MutableStateFlow<TokenStatus> = MutableStateFlow(TokenWaiting)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val tokens = tokenRepo.getTokens()
        tokenStatus.value = if (tokens != null) TokenSuccess(tokens) else TokenWaiting
        val authenticationRequest = getAuthRequest(request) // Добавляем токен в заголовок
        val response = chain.proceed(authenticationRequest) // делаем запрос
        return when (response.code) {
            401 -> {
                response.close()
                refresh(request, chain)
            }
            else -> response
        }
    }

    private fun refresh(
        originalRequest: Request,
        chain: Interceptor.Chain
    ): Response {
        Log.d(TAG, "refresh: $originalRequest $chain")
        if (tokenStatus.value is TokenWaiting) {
            return awaitRefreshingAndRequest(originalRequest, chain)
        } else {
            tokenStatus.value = TokenWaiting
            val responseNewTokens = getNewTokens().execute()

            return when {
                responseNewTokens.body() == null || responseNewTokens.code() != 200 -> {
                    Log.d(
                        TAG,
                        "responseNewTokens.body() == null || responseNewTokens.code() != 200"
                    )
                    tokenRepo.clearTokens()
                    tokenStatus.value = TokenError
                    navigator.navigateToAuth()
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
                    tokens?.accessToken?.let {
                        tokenRepo.saveTokens(tokens)
                        tokenStatus.value = TokenSuccess(tokens)
                    }.onNull {
                        throw EmptyTokensWhileRefreshing
                    }

                    Log.d(
                        TAG,
                        "responseNewTokens.body() != null || responseNewTokens.code() == 200"
                    )

                    val newAuthenticationRequest = getAuthRequest(originalRequest)
                    runBlocking {
                        chain.proceed(newAuthenticationRequest)
                    }
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
        Log.d(TAG, "awaitRefreshingAndRequest: $originalRequest")
        runBlocking {
            tokenStatus.collect { tokenStatus ->
                Log.d(TAG, "collect tokenStatus: $tokenStatus")

                if (tokenStatus is TokenSuccess) {
                    val newAuthenticationRequest = getAuthRequest(originalRequest)
                    chain.proceed(newAuthenticationRequest)
                }
            }
        }
    }

    private fun getAuthRequest(originalRequest: Request) = originalRequest
        .newBuilder()
        .addHeader(
            tokenHeader,
            tokenAdder + tokenRepo.getTokens()?.accessToken
        )
        .build()


}