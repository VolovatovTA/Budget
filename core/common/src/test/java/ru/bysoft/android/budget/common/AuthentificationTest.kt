package ru.bysoft.android.budget.common

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*
import ru.bysoft.android.budget.common.network.authentificator.AuthenticationInterceptorRefreshToken
import ru.bysoft.android.budget.common.network.authentificator.ICommonNavigation
import ru.bysoft.android.budget.common.network.authentificator.tokenHeader
import ru.bysoft.android.budget.common.token.ITokenStorage
import ru.bysoft.android.budget.common.token.entity.AuthSuccessResponse
import ru.bysoft.android.budget.common.token.network.ITokenRefreshApi

class AuthentificationTest {

    @Mock
    lateinit var navigator: ICommonNavigation

    @Mock
    lateinit var refreshApi: ITokenRefreshApi

    @Mock
    lateinit var tokenStorage: ITokenStorage

    @Mock
    private lateinit var chain: Interceptor.Chain

    @Mock
    private lateinit var request: Request

    @Mock
    private lateinit var successAuthResponse: Response

    @Mock
    private lateinit var requestBuilder: Request.Builder

    private val successResponseWithGoodTokens = AuthSuccessResponse("", "")

    private lateinit var authentificator: AuthenticationInterceptorRefreshToken

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)

        // Возвращаем request
        Mockito.`when`(chain.request()).doReturn(request)
        Mockito.`when`(tokenStorage.getTokens()).doReturn(flowOf(successResponseWithGoodTokens))
        Mockito.`when`(chain.proceed(any())).doReturn(successAuthResponse)
        Mockito.`when`(request.newBuilder()).doReturn(requestBuilder)
        Mockito.`when`(requestBuilder.addHeader(eq(tokenHeader), any())).doReturn(requestBuilder)
        Mockito.`when`(requestBuilder.build()).doReturn(request)
        runBlocking {
            Mockito.`when`(refreshApi.refresh(any())).doReturn(successResponseWithGoodTokens)
        }

        authentificator = AuthenticationInterceptorRefreshToken(
            refreshApi,
            tokenStorage,
            navigator,
        )
    }

    @Test
    fun startDataIsCorrect() {
        val response = authentificator.intercept(chain)
        assert(response == this.successAuthResponse)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun refreshSingleRequest() {
        Mockito.`when`(successAuthResponse.code).doReturn(401).doReturn(200)


        runTest {
            val response = authentificator.intercept(chain)
            verify(refreshApi).refresh(any())

            assert(response.code == 200)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val scope = TestScope()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun refreshOneRequestWhileTwo() {
        Mockito.`when`(successAuthResponse.code)
            .doReturn(401)
            .doReturn(401)
            .doReturn(401)
            .doReturn(200)

//        (0..12).map {
//            println("Start request $it")
//            scope.async {
//                val response = chain.proceed(any())
//                if (it == 0) {
//                    assert(response == this@AuthentificationTest.expiredAccessTokeResponse)
//                } else {
//                    assert(response == this@AuthentificationTest.successAuthResponse)
//                }
//            }
//        }

        runTest(dispatchTimeoutMs = 3000) {
            (0..5).map {
                println("Start request $it")
                scope.async {
                    val response = authentificator.intercept(chain)
                    assert(response == this@AuthentificationTest.successAuthResponse)
                }
            }.awaitAll()
            verify(refreshApi, times(1)).refresh(any())
        }
    }
}