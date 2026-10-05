package ru.bysoft.android.budget.features.create_update_delete_transactions.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.*


val TransactionsDi = module {
    singleOf(::TransactionsCategoryPresentationMapper) bind ITransactionsCategoryPresentationMapper::class
    singleOf(::TransactionsWalletPresentationMapper) bind ITransactionWalletPresentationMapper::class
    singleOf(::TransactionPresentationMapper) bind ITransactionPresentationMapper::class
}