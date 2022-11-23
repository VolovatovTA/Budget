package ru.bysoft.budget.home.data.mapper

import ru.bysoft.budget.home.data.entity.WalletsData
import ru.bysoft.budget.home.data.network.entity.WalletsResponse
import java.util.Currency

fun List<WalletsResponse>.mapToData() = this.map { it.mapToData() }

fun WalletsResponse.mapToData() = WalletsData(
    balance = this.balance ?: 0f,
    currency = currency.mapToCurrency(),
    name = this.name ?: "",
)

fun String?.mapToCurrency(): Currency =
    when (this) {
        "RUR" -> Currency.getInstance(this)
        "USD" -> Currency.getInstance(this)
        "GEL" -> Currency.getInstance(this)
        "AMD" -> Currency.getInstance(this)
        else -> Currency.getInstance(this)
    }