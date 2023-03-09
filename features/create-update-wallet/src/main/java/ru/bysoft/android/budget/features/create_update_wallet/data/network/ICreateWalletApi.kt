package ru.bysoft.android.budget.features.create_update_wallet.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import ru.bysoft.android.budget.features.create_update_wallet.data.network.entity.CreateWalletRequest
import ru.bysoft.android.budget.features.create_update_wallet.data.network.entity.CreateWalletSuccessResponse

const val pathToCreateWallet = "wallet/api/v1/wallets"

interface ICreateWalletApi {
    @POST(pathToCreateWallet)
    suspend fun createWallet(@Body request: CreateWalletRequest): CreateWalletSuccessResponse
}