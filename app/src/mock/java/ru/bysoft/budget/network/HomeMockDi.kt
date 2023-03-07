package ru.bysoft.budget.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.bysoft.budget.features.bottom_navigation.home.data.me.network.HomeMeApiMock
import ru.bysoft.budget.features.bottom_navigation.home.data.me.network.IHomeMeApi
import ru.bysoft.budget.features.bottom_navigation.home.data.transactions.network.ITransactionsApi
import ru.bysoft.budget.features.bottom_navigation.home.data.transactions.network.TransactionApiMock
import ru.bysoft.budget.features.bottom_navigation.home.data.wallets.network.HomeWalletsApiMock
import ru.bysoft.budget.features.bottom_navigation.home.data.wallets.network.IHomeWalletsApi

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeMockDi {
    @Binds
    abstract fun bindWalletsApiMock(api: HomeWalletsApiMock): IHomeWalletsApi

    @Binds
    abstract fun bindMeApiMock(api: HomeMeApiMock): IHomeMeApi

    @Binds
    abstract fun bindTransactionApiMock(api: TransactionApiMock): ITransactionsApi
}