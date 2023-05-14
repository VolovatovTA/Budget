package ru.budget.android.api.data.source.mock

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import ru.budget.android.api.data.source.network.IAuthApi
import ru.budget.android.api.data.source.network.entity.auth.SignInGoogleRequest
import ru.budget.android.api.data.source.network.entity.auth.SignInRequest
import ru.budget.android.api.data.source.network.entity.auth.SignUpRequest
import ru.budget.android.api.data.source.network.postSignInRoute
import ru.budget.android.api.data.source.network.postSignUpRoute
import ru.bysoft.android.budget.common.network.MOCK_DELAY_NAME
import ru.bysoft.android.budget.common.token.entity.AuthSuccessResponse
import ru.bysoft.android.budget.common.util.getStringFromAsset
import ru.bysoft.android.budget.common.util.pointJson
import ru.bysoft.android.budget.common.util.restore
import javax.inject.Inject
import javax.inject.Named

class AuthApiMock @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named(MOCK_DELAY_NAME) private val mockDelay: Long
) : IAuthApi {

    override suspend fun signIn(request: SignInRequest): AuthSuccessResponse {
        delay(mockDelay)
        return context.getStringFromAsset(postSignInRoute + pointJson).restore()
    }

    override suspend fun signUp(request: SignUpRequest): AuthSuccessResponse {
        delay(mockDelay)
        return context.getStringFromAsset(postSignUpRoute + pointJson).restore()
    }

    override suspend fun signInByGoogle(request: SignInGoogleRequest): AuthSuccessResponse {
        return AuthSuccessResponse(

        )
    }

}