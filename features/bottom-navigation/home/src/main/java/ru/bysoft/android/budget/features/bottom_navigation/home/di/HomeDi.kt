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

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeDi {

    @Binds
    abstract fun bindWalletsRepo(repo: HomeWalletsRepo): IHomeWalletsRepo

    @Binds
    abstract fun bindMeRepo(repo: HomeMeRepo): IHomeMeRepo

    @Binds
    abstract fun bindTransactionsRepo(repo: TransactionRepo): ITransactionsRepo
}