package ru.bysoft.budget.common.network.authentificator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import ru.bysoft.budget.auth.data.network.entity.SignInErrorResponse
import ru.bysoft.budget.common.token.ITokenRepo
import ru.bysoft.budget.common.token.entity.TokenRefreshRequest
import ru.bysoft.budget.common.token.network.ITokenRefreshApi
import ru.bysoft.budget.common.util.restore
import java.io.IOException
import javax.inject.Inject

object ThrowableWhenTryingRefreshToken : Throwable()
class UnknownSlugMessage(slug: String?) : Throwable(slug)

const val tokenHeader = "X-API-Token"

class AuthenticationInterceptorRefreshToken @Inject constructor(
    private val refreshApi: ITokenRefreshApi,
    private val tokenRepo: ITokenRepo
) :
    Interceptor {

    val qeue = emptyMap<String, Any>()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //MAKE SYNCHRONIZED
        val originalRequest = chain.request()
        val authenticationRequest = originalRequest.newBuilder()
            .addHeader(
                tokenHeader,
                tokenRepo.getAccessToken()?: "empty-access-token"
            )
            .build()
        val initialResponse = chain.proceed(authenticationRequest)

        when {
            initialResponse.code() == 401 -> {
                val slug = initialResponse.body()!!.string().restore<SignInErrorResponse>().slug
                val responseNewTokens = if (slug == "invalid-token") {
                    //RUN BLOCKING!!
                    runBlocking(Dispatchers.IO) {
                        refreshApi
                            .refresh(
                                TokenRefreshRequest(
                                    tokenRepo.getRefreshToken() ?: "empty-refresh-token"
                                )
                            )
                            .execute()
                    }
                } else throw UnknownSlugMessage(slug)


                return when {
                    responseNewTokens.body() == null || responseNewTokens.code() != 200 -> {
                        tokenRepo.clearTokens()
                        throw ThrowableWhenTryingRefreshToken
                    }
                    else -> {
                        responseNewTokens.body()?.accessToken?.let {
                            tokenRepo.saveTokens(responseNewTokens.body()!!)
                        }
                        val newAuthenticationRequest = originalRequest.newBuilder().addHeader(
                            "X-Api-Token", responseNewTokens.body()?.accessToken ?: ""
                        ).build()
                        chain.proceed(newAuthenticationRequest)
                    }
                }
            }
            else -> return initialResponse
        }

    }

}