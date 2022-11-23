package ru.bysoft.budget.home.data.network

import retrofit2.http.GET
import ru.bysoft.budget.home.data.network.entity.WalletsResponse

const val pathWalletList = "wallet/api/v1/wallets"
const val pathSettings = "users/api/v1/me"
interface IHomeWalletsApi {

    @GET(pathSettings)
    suspend fun getWallets(): List<WalletsResponse>
}