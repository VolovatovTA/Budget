package ru.bysoft.android.budget.features.create_update_wallet.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.bysoft.android.budget.features.create_update_wallet.IWalletScreenController
import ru.bysoft.android.budget.features.create_update_wallet.data.WalletRepository
import ru.bysoft.android.budget.features.create_update_wallet.data.IWalletRepository
import ru.bysoft.android.budget.features.create_update_wallet.presentation.controllers.WalletScreenController

@Module
@InstallIn(ViewModelComponent::class)
interface CreateWalletHiltModule {
    @Binds
    fun bindCreateWalletRepo(impl: WalletRepository): IWalletRepository

    @Binds
    fun bindWalletUiController(impl: WalletScreenController): IWalletScreenController
}