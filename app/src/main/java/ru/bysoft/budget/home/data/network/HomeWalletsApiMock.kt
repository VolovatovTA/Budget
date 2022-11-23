package ru.bysoft.budget.home.data.network

import ru.bysoft.budget.home.data.network.entity.WalletsResponse
import javax.inject.Inject

class HomeWalletsApiMock @Inject constructor(): IHomeWalletsApi {

    override suspend fun getWallets(): List<WalletsResponse> {
        TODO("Not yet implemented")
    }

}