package ru.bysoft.budget.features.bottom_navigation.home.data.wallets.mapper

import ru.bysoft.budget.features.bottom_navigation.home.data.wallets.entity.WalletData
import ru.bysoft.budget.features.bottom_navigation.home.data.wallets.network.entity.WalletResponse
import ru.bysoft.budget.features.bottom_navigation.home.data.wallets.network.entity.WalletsResponse

fun WalletsResponse.mapToData() = this.data!!.map { it.mapToData() }

fun WalletResponse.mapToData() = WalletData(
    balance = this.balance ?: 0f,
    currency = this.currency ?: "",
    name = this.name ?: "",
    id = this.id!!
)