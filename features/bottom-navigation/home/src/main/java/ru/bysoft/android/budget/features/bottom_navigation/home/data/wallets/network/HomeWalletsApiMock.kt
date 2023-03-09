package ru.bysoft.android.budget.features.bottom_navigation.home.data.wallets.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import ru.bysoft.android.budget.common.network.MOCK_DELAY_NAME
import ru.bysoft.android.budget.common.util.getStringFromAsset
import ru.bysoft.android.budget.common.util.pointJson
import ru.bysoft.android.budget.common.util.restore
import ru.bysoft.android.budget.features.bottom_navigation.home.data.wallets.network.entity.WalletsResponse
import javax.inject.Inject
import javax.inject.Named

class HomeWalletsApiMock @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named(MOCK_DELAY_NAME) private val delay: Long
) : IHomeWalletsApi {

    var counter = 0
    override suspend fun getWallets(): WalletsResponse {
        delay(delay)

        val append = when {
            ++counter % 3 == 0 -> "_full"
            counter % 3 == 1 -> "1"
            else -> "_empty"
        }
        return context.getStringFromAsset(pathWalletList + append + pointJson).restore()
    }

}