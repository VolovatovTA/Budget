package ru.bysoft.budget.features.create_update_wallet.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.bysoft.budget.features.create_update_wallet.data.CreateWalletRepository
import ru.bysoft.budget.features.create_update_wallet.data.ICreateWalletRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class CreateWalletHiltModule {
    @Binds
    abstract fun bindCreateWalletRepo(repo: CreateWalletRepository): ICreateWalletRepository
}