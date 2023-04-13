package ru.bysoft.android.budget.features.create_update_wallet.presentation.mapper

import ru.bysoft.android.budget.features.create_update_wallet.data.entity.CreateWalletData
import ru.bysoft.android.budget.features.create_update_wallet.data.entity.CreateWalletErrorData
import ru.bysoft.android.budget.features.create_update_wallet.data.network.entity.CreateWalletErrorResponse
import ru.bysoft.android.budget.features.create_update_wallet.data.network.entity.CreateWalletRequest
import ru.bysoft.android.budget.features.create_update_wallet.data.network.entity.CreateWalletResponse
import ru.bysoft.android.budget.features.create_update_wallet.data.network.entity.CreateWalletSuccessResponse
import ru.bysoft.android.budget.features.create_update_wallet.presentation.entity.CreateWalletState

fun mapToRequest(state: CreateWalletState): CreateWalletRequest =
    CreateWalletRequest(
        balance = state.balanceTextState.text.toDouble(),
        currency = state.currencyFieldState.selectedCurrency?.iso4217!!,
        name = state.nameTextState.text
    )

fun mapToData(response: CreateWalletErrorResponse) =
    when (response.slug) {
        CreateWalletErrorData.INVALID_CURRENCY.slug -> CreateWalletData(errorType = CreateWalletErrorData.INVALID_CURRENCY)
        CreateWalletErrorData.INVALID_NAME.slug -> CreateWalletData(errorType = CreateWalletErrorData.INVALID_NAME)
        CreateWalletErrorData.INVALID_BALANCE.slug -> CreateWalletData(errorType = CreateWalletErrorData.INVALID_BALANCE)
        CreateWalletErrorData.NO_UNIQUE_NAME.slug -> CreateWalletData(errorType = CreateWalletErrorData.NO_UNIQUE_NAME)
        else -> throw Throwable("Unknown slug: ${response.slug}")
    }

fun mapToData(response: CreateWalletSuccessResponse) = CreateWalletData()

fun mapToData(response: CreateWalletResponse) =
    when (response) {
        is CreateWalletErrorResponse -> mapToData(response)
        is CreateWalletSuccessResponse -> mapToData(response)
    }