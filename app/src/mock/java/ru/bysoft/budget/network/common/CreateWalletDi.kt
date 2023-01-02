package ru.bysoft.budget.network.common

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.bysoft.budget.features.create_wallet.data.network.CreateWalletApiMock
import ru.bysoft.budget.features.create_wallet.data.network.ICreateWalletApi

@Module
@InstallIn(SingletonComponent::class)
abstract class CreateWalletDi {
    @Binds
    abstract fun bindCreateWalletApi(apiMock: CreateWalletApiMock): ICreateWalletApi
}