package ru.bysoft.budget.features.create_wallet.presentation.mapper

import ru.bysoft.budget.common.util.onNull
import ru.bysoft.budget.features.create_wallet.data.entity.CreateWalletData
import ru.bysoft.budget.features.create_wallet.data.entity.CreateWalletErrorData
import ru.bysoft.budget.features.create_wallet.data.network.entity.CreateWalletErrorResponse
import ru.bysoft.budget.features.create_wallet.data.network.entity.CreateWalletRequest
import ru.bysoft.budget.features.create_wallet.data.network.entity.CreateWalletResponse
import ru.bysoft.budget.features.create_wallet.data.network.entity.CreateWalletSuccessResponse
import ru.bysoft.budget.features.create_wallet.presentation.entity.CreateWalletState

fun CreateWalletState.mapToRequest() =
    CreateWalletRequest(
        balance = this.balanceTextState.text.toDouble(),
        currency = this.currencyFieldState.selectedCurrency.iso4217,
        name = this.nameTextState.text
    )

fun CreateWalletErrorResponse.mapToData() =
    when (slug) {
        "invalid-currency" -> CreateWalletData(errorType = CreateWalletErrorData.INVALID_CURRENCY)
        "invalid-name" -> CreateWalletData(errorType = CreateWalletErrorData.INVALID_NAME)
        "invalid-balance" -> CreateWalletData(errorType = CreateWalletErrorData.INVALID_BALANCE)
        else -> throw Throwable("Unknown slug: $slug")
    }

fun CreateWalletSuccessResponse.mapToData() = CreateWalletData()

fun CreateWalletResponse.mapToData() =
    when(this){
        is CreateWalletErrorResponse -> this.mapToData()
        is CreateWalletSuccessResponse -> this.mapToData()
    }