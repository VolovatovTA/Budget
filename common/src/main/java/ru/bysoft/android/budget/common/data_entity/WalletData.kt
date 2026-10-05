package ru.bysoft.android.budget.common.data_entity

import ru.bysoft.android.budget.common.R
data class WalletData(
    val balance: Float,
    val currency: String,
    val name: String,
    val id: String,
    val iconName: String?
)

class CreateWalletErrorData(val errorText: Int, val slug: String) : Throwable(slug) {
    companion object {
        val INVALID_CURRENCY = CreateWalletErrorData(R.string.invalid_currency, "invalid-currency")
        val INVALID_BALANCE = CreateWalletErrorData(R.string.invalid_balance, "invalid-balance")
        val INVALID_NAME = CreateWalletErrorData(R.string.invalid_name, "invalid-name")
        val NO_UNIQUE_NAME = CreateWalletErrorData(
            R.string.wallet_name_must_be_unique,
            "wallet-name-musq-be-unique"
        )
    }
}