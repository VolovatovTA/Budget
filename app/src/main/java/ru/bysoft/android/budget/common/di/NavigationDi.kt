package ru.bysoft.android.budget.common.di

import android.content.Context
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.bysoft.android.budget.auth.navigation.IAuthNavigation
import ru.bysoft.android.budget.common.navigation.auth.AuthNavigation
import ru.bysoft.android.budget.common.navigation.common.CommonNavigation
import ru.bysoft.android.budget.common.navigation.create_update_categiry.CreateUpdateCategoryNavigation
import ru.bysoft.android.budget.common.navigation.create_wallet.CreateWalletNavigation
import ru.bysoft.android.budget.common.navigation.home.HomeNavigation
import ru.bysoft.android.budget.common.navigation.splash.SplashNavigation
import ru.bysoft.android.budget.common.navigation.statistic.StatisticNavigation
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.transaction.TransactionNavigation
import ru.bysoft.android.budget.common.network.authentificator.ICommonNavigation
import ru.bysoft.android.budget.features.create_udate_category.navigation.ICreateUpdateCategoryNavigation
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.ITransactionNavigation
import ru.bysoft.android.budget.features.bottom_navigation.home.navigation.IHomeNavigation
import ru.bysoft.android.budget.features.bottom_navigation.statistic.navigation.IStatisticNavigation
import ru.bysoft.android.budget.features.create_update_wallet.navigation.ICreateWalletNavigation
import ru.bysoft.android.budget.features.splash.navigation.ISplashNavigation
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationDi {
    companion object {
        @Provides
        @Singleton
        fun provideNavHostController(@ApplicationContext context: Context): NavHostController =
            NavHostController(context).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
                navigatorProvider.addNavigator(DialogNavigator())
            }
    }

    @Binds
    abstract fun bindAuthNavigation(navigation: AuthNavigation): IAuthNavigation

    @Binds
    abstract fun bindHomeNavigation(navigation: HomeNavigation): IHomeNavigation

    @Binds
    abstract fun bindCreateWalletNavigation(navigation: CreateWalletNavigation): ICreateWalletNavigation

    @Binds
    abstract fun bindSplashNavigation(navigation: SplashNavigation): ISplashNavigation

    @Binds
    abstract fun bindCommonNavigation(navigation: CommonNavigation): ICommonNavigation

    @Binds
    abstract fun bindStatisticNavigation(navigation: StatisticNavigation): IStatisticNavigation

    @Binds
    abstract fun bindCreateUpdateCategoryNavigation(navigation: CreateUpdateCategoryNavigation): ICreateUpdateCategoryNavigation

    @Binds
    abstract fun bindTransactionNavigation(navigation: TransactionNavigation): ITransactionNavigation

}