package ru.budget.android.api.data.mapper

import ru.budget.android.api.data.source.network.entity.wallet.WalletItemResponse
import ru.budget.android.api.data.source.network.entity.wallet.WalletListResponse
import ru.bysoft.android.budget.common.data_entity.WalletData

interface IWalletsDataMapper {
    fun mapToData(response: WalletListResponse?): List<WalletData>
}

class WalletsDataMapper: IWalletsDataMapper {
    override fun mapToData(response: WalletListResponse?): List<WalletData> {
        return response?.data?.map { mapToData(it) } ?: emptyList()
    }

    fun mapToData(response: WalletItemResponse): WalletData {
        return WalletData(
            balance = response.balance?.toFloatOrNull() ?: 0f,
            currency = response.currency ?: "",
            name = response.name ?: "",
            id = response.id!!,
            iconName = response.iconName
        )
    }
}