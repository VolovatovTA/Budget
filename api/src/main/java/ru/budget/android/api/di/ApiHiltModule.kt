package ru.budget.android.api.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.budget.android.api.data.mapper.ITransactionsDataMapper
import ru.budget.android.api.data.mapper.IWalletsDataMapper
import ru.budget.android.api.data.mapper.TransactionsDataMapper
import ru.budget.android.api.data.mapper.WalletsDataMapper

@Module
@InstallIn(SingletonComponent::class)
interface ApiHiltModule {
    @Binds
    fun bindTransactionsDataMapper(impl: TransactionsDataMapper): ITransactionsDataMapper

    @Binds
    fun bindMapper(impl: WalletsDataMapper): IWalletsDataMapper
}