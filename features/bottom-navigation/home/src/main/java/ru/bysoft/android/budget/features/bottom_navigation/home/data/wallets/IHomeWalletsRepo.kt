package ru.bysoft.android.budget.features.bottom_navigation.home.data.wallets

import ru.budget.android.api.data.mapper.IWalletsDataMapper
import ru.budget.android.api.data.source.network.IWalletApi
import ru.bysoft.android.budget.common.data_entity.WalletData
import javax.inject.Inject

interface IHomeWalletsRepo {
    suspend fun getWallets(): List<WalletData>
}

class HomeWalletsRepo @Inject constructor(
    private val api: IWalletApi,
    private val mapper: IWalletsDataMapper
) : IHomeWalletsRepo {

    override suspend fun getWallets(): List<WalletData> {
        return mapper.mapToData(api.getWallets())
    }

}