package ru.bysoft.budget.features.create_update_wallet.data

import retrofit2.HttpException
import ru.bysoft.budget.common.util.onNull
import ru.bysoft.budget.common.util.restore
import ru.bysoft.budget.features.create_update_wallet.data.entity.CreateWalletData
import ru.bysoft.budget.features.create_update_wallet.data.network.ICreateWalletApi
import ru.bysoft.budget.features.create_update_wallet.data.network.entity.CreateWalletErrorResponse
import ru.bysoft.budget.features.create_update_wallet.presentation.entity.CreateWalletState
import ru.bysoft.budget.features.create_update_wallet.presentation.mapper.mapToData
import ru.bysoft.budget.features.create_update_wallet.presentation.mapper.mapToRequest
import javax.inject.Inject


interface ICreateWalletRepository {
    suspend fun createWallet(state: CreateWalletState): CreateWalletData
}

class CreateWalletRepository @Inject constructor(
    private val api: ICreateWalletApi,
) : ICreateWalletRepository {
    override suspend fun createWallet(state: CreateWalletState): CreateWalletData {
        return try {
            api.createWallet(state.mapToRequest()).mapToData()
        } catch (t: HttpException) {
            t.response()?.let { response ->
                response.errorBody()?.let { responseBody ->
                    responseBody.string()
                                        .restore<CreateWalletErrorResponse>()?.mapToData()
                }
            }.onNull { throw Throwable("can't recognize $t as CreateWalletErrorResponse") }
        }
    }

}