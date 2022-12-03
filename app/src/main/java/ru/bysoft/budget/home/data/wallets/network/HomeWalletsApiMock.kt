package ru.bysoft.budget.home.data.wallets.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import ru.bysoft.budget.common.network.MOCK_DELAY_NAME
import ru.bysoft.budget.common.util.getStringFromAsset
import ru.bysoft.budget.common.util.pointJson
import ru.bysoft.budget.common.util.restore
import ru.bysoft.budget.home.data.wallets.network.entity.WalletsResponse
import javax.inject.Inject
import javax.inject.Named

class HomeWalletsApiMock @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named(MOCK_DELAY_NAME) private val delay: Long
): IHomeWalletsApi {

    override suspend fun getWallets(): WalletsResponse {
        delay(delay)
        return context.getStringFromAsset(pathWalletList + pointJson).restore()
    }

}