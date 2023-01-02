package ru.bysoft.budget.common.di

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.bysoft.budget.auth.navigation.IAuthNavigation
import ru.bysoft.budget.common.navigation.auth.AuthNavigation
import ru.bysoft.budget.common.navigation.common.CommonNavigation
import ru.bysoft.budget.common.navigation.create_wallet.CreateWalletNavigation
import ru.bysoft.budget.common.navigation.home.HomeNavigation
import ru.bysoft.budget.common.navigation.splash.SplashNavigation
import ru.bysoft.budget.common.network.authentificator.ICommonNavigation
import ru.bysoft.budget.features.bottom_navigation.home.navigation.IHomeNavigation
import ru.bysoft.budget.features.create_wallet.navigation.ICreateWalletNavigation
import ru.bysoft.budget.splash.navigation.ISplashNavigation
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
}