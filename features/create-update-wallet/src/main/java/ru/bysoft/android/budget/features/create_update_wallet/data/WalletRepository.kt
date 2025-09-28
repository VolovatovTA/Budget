package ru.bysoft.android.budget.features.create_update_wallet.data

import retrofit2.HttpException
import ru.budget.android.api.data.mapper.WalletsDataMapper
import ru.budget.android.api.data.source.network.IWalletApi
import ru.bysoft.android.budget.common.util.onNull
import ru.bysoft.android.budget.common.util.restore
import ru.budget.android.api.data.source.network.entity.wallet.WalletErrorResponse
import ru.bysoft.android.budget.features.create_update_wallet.presentation.entity.ControllerWalletState
import ru.budget.android.api.data.mapper.mapToData
import ru.budget.android.api.data.source.network.entity.wallet.CreateWalletRequest
import ru.budget.android.api.data.source.network.entity.wallet.UpdateWalletRequest
import ru.bysoft.android.budget.common.data_entity.WalletData


interface IWalletRepository {
    suspend fun createWallet(state: ControllerWalletState): Result<Unit>
    suspend fun getWalletData(id: String): WalletData
    suspend fun updateWallet(walletId: String, state: ControllerWalletState): Result<Unit>
    suspend fun deleteWallet(id: String)
}

class WalletRepository constructor(
    private val api: IWalletApi,
    private val mapper: WalletsDataMapper,
) : IWalletRepository {
    override suspend fun createWallet(state: ControllerWalletState): Result<Unit> {
        return try {
            api.createWallet(mapToCreateRequest(state))
            Result.success(Unit)
        } catch (t: HttpException) {
            t.response()?.let { response ->
                response.errorBody()?.let { responseBody ->
                    Result.failure<Unit>(
                        mapToData(responseBody.string().restore<WalletErrorResponse>())
                    )
                }
            }.onNull { throw Throwable("can't recognize $t as CreateWalletErrorResponse") }
        }
    }

    override suspend fun getWalletData(id: String): WalletData {
        return mapper.mapToData(api.getWallet(id))
    }

    override suspend fun updateWallet(walletId: String, state: ControllerWalletState): Result<Unit> {
        return try {
            api.updateWallet(walletId, mapToUpdateRequest(state))
            Result.success(Unit)
        } catch (t: HttpException) {
            t.response()?.let { response ->
                response.errorBody()?.let { responseBody ->
                    Result.failure<Unit>(
                        mapToData(responseBody.string().restore<WalletErrorResponse>())
                    )
                }
            }.onNull { throw Throwable("can't recognize $t as CreateWalletErrorResponse") }
        }
    }

    override suspend fun deleteWallet(id: String) {
        api.deleteWallet(id)
    }

}


fun mapToCreateRequest(state: ControllerWalletState): CreateWalletRequest =
    CreateWalletRequest(
        balance = state.balanceTextState.text.toFloatOrNull(),
        currency = state.currencyFieldState.selectedCurrency?.iso4217!!,
        name = state.nameTextState.text,
        iconName = state.iconState.iconName
    )

fun mapToUpdateRequest(state: ControllerWalletState): UpdateWalletRequest =
    UpdateWalletRequest(
        name = state.nameTextState.text,
        iconName = state.iconState.iconName
    )