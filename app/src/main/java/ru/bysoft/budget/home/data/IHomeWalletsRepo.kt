package ru.bysoft.budget.home.data

import ru.bysoft.budget.home.data.entity.WalletsData
import ru.bysoft.budget.home.data.mapper.mapToData
import ru.bysoft.budget.home.data.network.IHomeWalletsApi
import javax.inject.Inject

interface IHomeWalletsRepo {
    suspend fun getWallets(): List<WalletsData>
}

class HomeWalletsRepo @Inject constructor(
    private val api: IHomeWalletsApi
) : IHomeWalletsRepo {

    override suspend fun getWallets(): List<WalletsData> {

        return api.getWallets().mapToData()
    }

}