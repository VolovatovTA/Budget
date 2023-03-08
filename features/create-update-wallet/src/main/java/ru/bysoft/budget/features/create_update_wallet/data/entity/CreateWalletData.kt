package ru.bysoft.budget.features.create_update_wallet.data.entity

import ru.bysoft.budget.features.create_wallet.R

data class CreateWalletData(
    val errorType: CreateWalletErrorData? = null
)

enum class CreateWalletErrorData(val errorText: Int, val slug: String) {

    INVALID_CURRENCY(R.string.invalid_currency,"invalid-currency"),
    INVALID_BALANCE(R.string.invalid_balance,"invalid-balance"),
    INVALID_NAME(R.string.invalid_name,"invalid-name"),
    NO_UNIQUE_NAME(R.string.wallet_name_must_be_unique,"wallet-name-musq-be-unique"),
}