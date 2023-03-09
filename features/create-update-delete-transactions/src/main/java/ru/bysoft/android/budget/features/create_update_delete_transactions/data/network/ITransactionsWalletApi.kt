package ru.bysoft.android.budget.features.create_update_delete_transactions.data.network

import retrofit2.http.GET
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.entity.responses.TransactionWalletResponse

const val pathWalletList = "wallet/api/v1/wallets"

interface ITransactionsWalletApi {
    @GET(pathWalletList)
    suspend fun getWallets(): TransactionWalletResponse

}