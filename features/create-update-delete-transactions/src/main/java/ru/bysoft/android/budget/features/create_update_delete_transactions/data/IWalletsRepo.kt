package ru.bysoft.android.budget.features.create_update_delete_transactions.data

import ru.budget.android.api.data.mapper.IWalletsDataMapper
import ru.budget.android.api.data.source.network.IWalletApi
import ru.bysoft.android.budget.common.data_entity.WalletData

interface IWalletsRepo {
    suspend fun getWallets(): List<WalletData>
}

class WalletsRepo(
    private val api: IWalletApi,
    private val mapper: IWalletsDataMapper,
) : IWalletsRepo {
    override suspend fun getWallets(): List<WalletData> = mapper.mapToData(api.getWallets())
}
