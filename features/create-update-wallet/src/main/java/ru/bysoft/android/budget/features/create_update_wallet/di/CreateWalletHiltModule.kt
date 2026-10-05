package ru.bysoft.android.budget.features.create_update_wallet.di


import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.bysoft.android.budget.features.create_update_wallet.IWalletScreenController
import ru.bysoft.android.budget.features.create_update_wallet.data.WalletRepository
import ru.bysoft.android.budget.features.create_update_wallet.data.IWalletRepository
import ru.bysoft.android.budget.features.create_update_wallet.presentation.controllers.WalletScreenController


val CreateWalletDi = module {
    singleOf(::WalletRepository) bind IWalletRepository::class
    singleOf(::WalletScreenController) bind IWalletScreenController::class
}