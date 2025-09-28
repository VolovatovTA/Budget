package ru.bysoft.android.budget.features.bottom_navigation.home.data.wallets

import ru.budget.android.api.data.mapper.IWalletsDataMapper
import ru.budget.android.api.data.source.network.IWalletApi
import ru.bysoft.android.budget.common.data_entity.WalletData

interface IHomeWalletsRepo {
    suspend fun getWallets(): List<WalletData>

    fun clearCash()
}

class HomeWalletsRepo(
    private val api: IWalletApi,
    private val mapper: IWalletsDataMapper
) : IHomeWalletsRepo {

    private var cash: List<WalletData>? = null
    override suspend fun getWallets(): List<WalletData> {
        val localVal = cash ?: mapper.mapToData(api.getWallets())
        cash = localVal
        return localVal
    }

    override fun clearCash() {
        cash = null
    }

}