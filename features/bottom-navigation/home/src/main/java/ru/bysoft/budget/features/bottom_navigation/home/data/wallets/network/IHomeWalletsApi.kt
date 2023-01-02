package ru.bysoft.budget.features.bottom_navigation.home.data.wallets.network

import retrofit2.http.GET
import ru.bysoft.budget.features.bottom_navigation.home.data.wallets.network.entity.WalletsResponse

const val pathWalletList = "wallet/api/v1/wallets"

interface IHomeWalletsApi {

    @GET(pathWalletList)
    suspend fun getWallets(): WalletsResponse

}