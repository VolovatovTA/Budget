package ru.bysoft.budget.home.data

import ru.bysoft.budget.home.data.wallets.entity.WalletData
import ru.bysoft.budget.home.data.wallets.mapper.mapToData
import ru.bysoft.budget.home.data.wallets.network.IHomeWalletsApi
import javax.inject.Inject

interface IHomeWalletsRepo {
    suspend fun getWallets(): List<WalletData>
}

class HomeWalletsRepo @Inject constructor(
    private val api: IHomeWalletsApi
) : IHomeWalletsRepo {

    override suspend fun getWallets(): List<WalletData> {

        return api.getWallets().mapToData()
    }

}