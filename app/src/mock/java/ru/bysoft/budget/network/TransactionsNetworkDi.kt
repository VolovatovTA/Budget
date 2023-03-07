package ru.bysoft.budget.network

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.bysoft.budget.create_update_delete_transactions.data.network.*

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