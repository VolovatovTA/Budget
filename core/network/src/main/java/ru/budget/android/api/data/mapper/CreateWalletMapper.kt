package ru.budget.android.api.data.mapper

import ru.budget.android.api.data.source.network.entity.wallet.WalletErrorResponse
import ru.bysoft.android.budget.common.data_entity.CreateWalletErrorData

fun mapToData(response: WalletErrorResponse): CreateWalletErrorData =
    when (response.slug) {
        CreateWalletErrorData.INVALID_CURRENCY.slug -> CreateWalletErrorData.INVALID_CURRENCY
        CreateWalletErrorData.INVALID_NAME.slug -> CreateWalletErrorData.INVALID_NAME
        CreateWalletErrorData.INVALID_BALANCE.slug -> CreateWalletErrorData.INVALID_BALANCE
        CreateWalletErrorData.NO_UNIQUE_NAME.slug -> CreateWalletErrorData.NO_UNIQUE_NAME
        else -> throw Throwable("Unknown slug: ${response.slug}")
    }
