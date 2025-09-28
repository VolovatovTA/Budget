package ru.bysoft.android.budget.features.bottom_navigation.home.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.bysoft.android.budget.features.bottom_navigation.home.HomeViewModel
import ru.bysoft.android.budget.features.bottom_navigation.home.IHomeViewModel
import ru.bysoft.android.budget.features.bottom_navigation.home.data.me.HomeMeRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.data.me.IHomeMeRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions.ITransactionsRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions.TransactionRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.data.wallets.HomeWalletsRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.data.wallets.IHomeWalletsRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.mapper.HomePresentationMapper
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.mapper.IHomePresentationMapper

val HomeDi = module {
    singleOf(::TransactionRepo) bind ITransactionsRepo::class
    singleOf(::HomePresentationMapper) bind IHomePresentationMapper::class
    singleOf(::HomeWalletsRepo) bind IHomeWalletsRepo::class
    singleOf(::HomeMeRepo) bind IHomeMeRepo::class
    viewModelOf(::HomeViewModel) bind IHomeViewModel::class
}