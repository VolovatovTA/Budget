package ru.bysoft.android.budget.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.*

@Module
@InstallIn(SingletonComponent::class)
abstract class TransactionsNetworkDi {
    @Binds
    abstract fun provideTransactionsCategoryApiMock(api: TransactionCategoryApiMock): ITransactionsCategoryApi

    @Binds
    abstract fun provideTransactionsWalletsApiMock(api: TransactionsWalletApi): ITransactionsWalletApi

    @Binds
    abstract fun provideTransactionsApi(api: TransactionApiMock ): ITransactionApi
}