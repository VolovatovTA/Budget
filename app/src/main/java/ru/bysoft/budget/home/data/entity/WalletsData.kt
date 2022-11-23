package ru.bysoft.budget.home.data.entity

import java.util.Currency

data class WalletsData(
    val balance: Float,
    val currency: Currency,
    val name: String
)
