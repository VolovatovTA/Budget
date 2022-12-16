package ru.bysoft.budget.features.create_wallet.data.entity

data class CreateWalletData(
    val errorType: CreateWalletErrorData? = null
)

enum class CreateWalletErrorData {
    INVALID_CURRENCY, INVALID_BALANCE, INVALID_NAME
}