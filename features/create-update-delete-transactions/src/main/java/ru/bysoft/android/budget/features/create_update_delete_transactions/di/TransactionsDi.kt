package ru.bysoft.android.budget.features.create_update_delete_transactions.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper.*
import ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels.TransactionCreateViewModel
import ru.bysoft.android.budget.features.create_update_delete_transactions.viewmodels.TransactionUpdateViewModel


val TransactionsDi = module {
    singleOf(::TransactionsCategoryPresentationMapper) bind ITransactionsCategoryPresentationMapper::class
    singleOf(::TransactionsWalletPresentationMapper) bind ITransactionWalletPresentationMapper::class
    singleOf(::TransactionPresentationMapper) bind ITransactionPresentationMapper::class
    viewModelOf(::TransactionCreateViewModel)
    viewModelOf(::TransactionUpdateViewModel)
}