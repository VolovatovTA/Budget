package ru.bysoft.android.budget.features.create_update_wallet.data.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import ru.bysoft.android.budget.common.network.MOCK_DELAY_NAME
import ru.bysoft.android.budget.common.util.getStringFromAsset
import ru.bysoft.android.budget.common.util.restore
import ru.bysoft.android.budget.features.create_update_wallet.data.network.entity.CreateWalletRequest
import ru.bysoft.android.budget.features.create_update_wallet.data.network.entity.CreateWalletSuccessResponse
import javax.inject.Inject
import javax.inject.Named

class CreateWalletApiMock @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named(MOCK_DELAY_NAME) private val mockDelay: Long
) : ICreateWalletApi {
    override suspend fun createWallet(request: CreateWalletRequest): CreateWalletSuccessResponse {
        delay(mockDelay)
        return context.getStringFromAsset(pathToCreateWallet).restore()
    }
}