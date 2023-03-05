package ru.bysoft.budget.features.bottom_navigation.home.data.wallets

import ru.bysoft.budget.common.network.authentificator.ThrowableWhenTryingRefreshToken
import ru.bysoft.budget.features.bottom_navigation.home.data.wallets.network.IHomeWalletsApi
import ru.bysoft.budget.features.bottom_navigation.home.data.wallets.entity.WalletData
import ru.bysoft.budget.features.bottom_navigation.home.data.wallets.mapper.mapToData
import javax.inject.Inject

interface IHomeWalletsRepo {
    suspend fun getWallets(): List<WalletData>
}

class HomeWalletsRepo @Inject constructor(
    private val api: IHomeWalletsApi
) : IHomeWalletsRepo {

    override suspend fun getWallets(): List<WalletData> {
        return api.getWallets().mapToData() ?: emptyList()
    }

}