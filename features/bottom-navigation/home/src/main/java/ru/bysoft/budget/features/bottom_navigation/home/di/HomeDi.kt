package ru.bysoft.budget.features.bottom_navigation.home.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.bysoft.budget.features.bottom_navigation.home.data.me.HomeMeRepo
import ru.bysoft.budget.features.bottom_navigation.home.data.me.IHomeMeRepo
import ru.bysoft.budget.features.bottom_navigation.home.data.transactions.ITransactionsRepo
import ru.bysoft.budget.features.bottom_navigation.home.data.transactions.TransactionRepo
import ru.bysoft.budget.features.bottom_navigation.home.data.wallets.HomeWalletsRepo
import ru.bysoft.budget.features.bottom_navigation.home.data.wallets.IHomeWalletsRepo
import java.util.Locale

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