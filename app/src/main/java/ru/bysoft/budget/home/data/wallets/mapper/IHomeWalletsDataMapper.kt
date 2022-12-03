package ru.bysoft.budget.home.data.wallets.mapper

import ru.bysoft.budget.home.data.wallets.entity.WalletData
import ru.bysoft.budget.home.data.wallets.network.entity.WalletResponse
import ru.bysoft.budget.home.data.wallets.network.entity.WalletsResponse
import java.util.Currency

fun WalletsResponse.mapToData() = this.data.map { it.mapToData() }

fun WalletResponse.mapToData() = WalletData(
    balance = this.balance ?: 0f,
    currency = currency.mapToCurrency(),
    name = this.name ?: "",
)

fun String?.mapToCurrency(): Currency =
    when (this) {
        "RUB" -> Currency.getInstance(this)
        "USD" -> Currency.getInstance(this)
        "GEL" -> Currency.getInstance(this)
        "AMD" -> Currency.getInstance(this)
        else -> Currency.getInstance(this)
    }