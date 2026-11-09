package ru.budget.android.api.data.source.mock

import android.content.Context
import kotlinx.coroutines.delay
import ru.budget.android.api.data.source.network.IMeApi
import ru.budget.android.api.data.source.network.entity.me.MeResponse
import ru.budget.android.api.data.source.network.entity.me.SettingsRequest
import ru.budget.android.api.data.source.network.pathMe
import ru.bysoft.android.budget.common.util.getStringFromAsset
import ru.bysoft.android.budget.common.util.pointJson
import ru.bysoft.android.budget.common.util.restore

class MeApiMock(
    private val context: Context,
    private val delayMock: Long
) : IMeApi {

    override suspend fun getMeInfo(): MeResponse {
        delay(delayMock)
        return context.getStringFromAsset(pathMe + pointJson).restore()
    }

    override suspend fun setMeInfo(request: SettingsRequest) = Unit

    override suspend fun deleteAccount() = Unit
}
