package ru.bysoft.android.budget.features.transaction_detail.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.bysoft.android.budget.features.transaction_detail.data.ITransactionDetailRepo
import ru.bysoft.android.budget.features.transaction_detail.data.TransactionDetailRepo
import ru.bysoft.android.budget.features.transaction_detail.presentation.TransactionDetailViewModel
import ru.bysoft.android.budget.features.transaction_detail.presentation.mapper.TransactionDetailPresentationMapper

val TransactionDetailDi = module {
    singleOf(::TransactionDetailRepo) bind ITransactionDetailRepo::class
    singleOf(::TransactionDetailPresentationMapper)
    viewModelOf(::TransactionDetailViewModel)
}
