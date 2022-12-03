package ru.bysoft.budget.home.presentation.mapper

import ru.bysoft.budget.home.data.wallets.entity.WalletData
import ru.bysoft.budget.home.presentation.entity.WalletState

fun List<WalletData>.mapToState() = this.map { it.mapToState() }

fun WalletData.mapToState() = WalletState(
    name = this.name,
    balance = this.balance.toString(),
    currency = this.currency.symbol,
    backgroundColor = "col3"
)