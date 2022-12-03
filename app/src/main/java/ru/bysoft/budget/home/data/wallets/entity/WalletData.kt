package ru.bysoft.budget.home.data.wallets.entity

import java.util.Currency

data class WalletData(
    val balance: Float,
    val currency: Currency,
    val name: String
)
