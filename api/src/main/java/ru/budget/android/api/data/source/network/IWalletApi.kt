package ru.budget.android.api.data.source.network

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.budget.android.api.data.source.network.entity.wallet.*

const val pathWallet = "api/v1/wallets"
const val idPlacement = "idPlacement"

interface IWalletApi {
    @GET(pathWallet)
    suspend fun getWallets(): WalletListResponse

    @POST(pathWallet)
    suspend fun createWallet(@Body request: CreateWalletRequest): WalletItemResponse

    @PUT ("$pathWallet/{$idPlacement}")
    suspend fun updateWallet(@Path(idPlacement) id: String, @Body request: UpdateWalletRequest)

    @DELETE("$pathWallet/{$idPlacement}")
    suspend fun deleteWallet(@Path(idPlacement) id: String)

    @GET("$pathWallet/{$idPlacement}")
    suspend fun getWallet(@Path(idPlacement) id: String): WalletItemResponse

}