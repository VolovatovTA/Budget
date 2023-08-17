package ru.bysoft.android.budget.features.bottom_navigation.home.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.bysoft.android.budget.features.bottom_navigation.home.data.me.HomeMeRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.data.me.IHomeMeRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions.ITransactionsRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.data.transactions.TransactionRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.data.wallets.HomeWalletsRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.data.wallets.IHomeWalletsRepo
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.mapper.HomePresentationMapper
import ru.bysoft.android.budget.features.bottom_navigation.home.presentation.mapper.IHomePresentationMapper

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeDi {

    @Binds
    abstract fun bindTransactionsRepo(repo: TransactionRepo): ITransactionsRepo

    @Binds
    abstract fun bindHomePresentationMapper(impl: HomePresentationMapper): IHomePresentationMapper

    @Binds
    abstract fun bindHomeWalletsRepo(repo: HomeWalletsRepo): IHomeWalletsRepo

    @Binds
    abstract fun bindHomeMeRepo(repo: HomeMeRepo): IHomeMeRepo
}