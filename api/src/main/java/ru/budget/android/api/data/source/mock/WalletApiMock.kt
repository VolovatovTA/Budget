package ru.budget.android.api.data.source.mock

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import ru.budget.android.api.data.source.network.IWalletApi
import ru.bysoft.android.budget.common.network.MOCK_DELAY_NAME
import ru.bysoft.android.budget.common.util.getStringFromAsset
import ru.bysoft.android.budget.common.util.restore
import ru.budget.android.api.data.source.network.entity.wallet.CreateWalletRequest
import ru.budget.android.api.data.source.network.entity.wallet.UpdateWalletRequest
import ru.budget.android.api.data.source.network.entity.wallet.WalletItemResponse
import ru.budget.android.api.data.source.network.entity.wallet.WalletListResponse
import ru.budget.android.api.data.source.network.pathToWallet
import javax.inject.Inject
import javax.inject.Named

class WalletApiMock @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named(MOCK_DELAY_NAME) private val mockDelay: Long
) : IWalletApi {
    override suspend fun createWallet(request: CreateWalletRequest): WalletItemResponse {
        delay(mockDelay)
        return context.getStringFromAsset(pathToWallet).restore()
    }

    override suspend fun updateWallet(id: String, request: UpdateWalletRequest) {
        delay(mockDelay)
    }


    override suspend fun deleteWallet(id: String) {
        delay(mockDelay)
    }

    override suspend fun getWallet(id: String): WalletItemResponse {
        delay(mockDelay)
        return context.getStringFromAsset(pathToWallet).restore()
    }

    override suspend fun getWallets(): WalletListResponse {
        delay(mockDelay)
        return context.getStringFromAsset("").restore()
    }
}