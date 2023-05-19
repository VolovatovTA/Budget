package ru.budget.android.api.data.source.mock

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import ru.budget.android.api.data.source.network.IMeApi
import ru.bysoft.android.budget.common.network.MOCK_DELAY_NAME
import ru.bysoft.android.budget.common.util.TAG
import ru.bysoft.android.budget.common.util.getStringFromAsset
import ru.bysoft.android.budget.common.util.pointJson
import ru.bysoft.android.budget.common.util.restore
import ru.budget.android.api.data.source.network.entity.me.MeResponse
import ru.budget.android.api.data.source.network.entity.me.SettingsRequest
import ru.budget.android.api.data.source.network.pathSettings
import javax.inject.Inject
import javax.inject.Named

class MeApiMock @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named(MOCK_DELAY_NAME) private val delayMock: Long
) : IMeApi {

    override suspend fun getMeInfo(): MeResponse {
        delay(delayMock)
        Log.d(TAG, "getMeInfo: $pathSettings ${context.getStringFromAsset(pathSettings + pointJson)}")
        return context.getStringFromAsset(pathSettings + pointJson).restore()
    }

    override suspend fun setMeInfo(request: SettingsRequest) {

    }


}