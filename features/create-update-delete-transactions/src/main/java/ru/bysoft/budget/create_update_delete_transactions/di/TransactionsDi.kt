package ru.bysoft.budget.create_update_delete_transactions.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.bysoft.budget.create_update_delete_transactions.presentation.mapper.*

@Module
@InstallIn(SingletonComponent::class)
abstract class TransactionsDi {

    @Binds
    abstract fun bindCategoryMapper(mapper: TransactionsCategoryPresentationMapper): ITransactionsCategoryPresentationMapper

    @Binds
    abstract fun bindWalletMapper(mapper: TransactionsWalletPresentationMapper): ITransactionWalletPresentationMapper

    @Binds
    abstract fun bindTransactionMapper(mapper: TransactionPresentationMapper): ITransactionPresentationMapper
}