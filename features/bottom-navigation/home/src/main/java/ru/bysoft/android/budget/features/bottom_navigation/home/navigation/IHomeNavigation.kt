package ru.bysoft.android.budget.features.bottom_navigation.home.navigation

interface IHomeNavigation {
    fun toUpdateTransaction(id: String)
    fun toCreateWallet()
    fun toAuth()
    fun toEditWallet(walletId: String)
    fun toSettings()
}