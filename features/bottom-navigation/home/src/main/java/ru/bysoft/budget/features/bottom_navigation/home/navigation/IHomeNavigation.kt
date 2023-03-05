package ru.bysoft.budget.features.bottom_navigation.home.navigation

interface IHomeNavigation {
    fun toUpdateTransaction(id: String)
    fun toCreateWallet()
    fun toAuth()
}