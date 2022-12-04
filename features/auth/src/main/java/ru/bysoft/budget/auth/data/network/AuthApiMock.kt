package ru.bysoft.budget.auth.data.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import ru.bysoft.budget.auth.data.network.entity.SignInRequest
import ru.bysoft.budget.auth.data.network.entity.SignUpRequest
import ru.bysoft.budget.common.network.MOCK_DELAY_NAME
import ru.bysoft.budget.common.token.entity.AuthSuccessResponse
import ru.bysoft.budget.common.util.getStringFromAsset
import ru.bysoft.budget.common.util.pointJson
import ru.bysoft.budget.common.util.restore
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

}