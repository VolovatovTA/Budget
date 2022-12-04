package ru.bysoft.budget.features.bottom_navigation.home.presentation.mapper

import ru.bysoft.budget.common.util.getCurrency
import ru.bysoft.budget.features.bottom_navigation.home.data.wallets.entity.WalletData
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.WalletCardPresentation
import ru.bysoft.budget.features.bottom_navigation.home.presentation.entity.WalletCreateNewPresentation
import java.util.Currency

fun List<WalletData>.mapToState() = this.map { it.mapToState() }.plus(WalletCreateNewPresentation)

fun WalletData.mapToState() = WalletCardPresentation(
    name = this.name,
    balance = String.format(
        "%.${Currency.getInstance(this.currency).defaultFractionDigits}f",
        this.balance
    ),
    currency = getCurrency(this.currency)?.displayName ?: '*',
    backgroundColor = "col3",
    walletId = this.id
)