package ru.bysoft.budget.features.bottom_navigation.home.data.me.network

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import ru.bysoft.budget.common.network.MOCK_DELAY_NAME
import ru.bysoft.budget.common.util.TAG
import ru.bysoft.budget.common.util.getStringFromAsset
import ru.bysoft.budget.common.util.pointJson
import ru.bysoft.budget.common.util.restore
import ru.bysoft.budget.features.bottom_navigation.home.data.me.network.entity.MeResponse
import javax.inject.Inject
import javax.inject.Named

class HomeMeApiMock @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named(MOCK_DELAY_NAME) private val delayMock: Long
) : IHomeMeApi {

    override suspend fun getMeInfo(): MeResponse {
        delay(delayMock)
        Log.d(TAG, "getMeInfo: $pathSettings ${context.getStringFromAsset(pathSettings + pointJson)}")
        return context.getStringFromAsset(pathSettings + pointJson).restore()
    }


}