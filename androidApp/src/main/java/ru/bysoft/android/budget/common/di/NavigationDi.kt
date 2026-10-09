package ru.bysoft.android.budget.common.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.bysoft.android.budget.auth.navigation.IAuthNavigation
import ru.bysoft.android.budget.common.navigation.auth.AuthNavigation
import ru.bysoft.android.budget.common.navigation.common.CommonNavigation
import ru.bysoft.android.budget.common.navigation.create_update_categiry.CreateUpdateCategoryNavigation
import ru.bysoft.android.budget.common.navigation.create_update_transaction.TransactionNavigation
import ru.bysoft.android.budget.common.navigation.create_wallet.WalletNavigation
import ru.bysoft.android.budget.common.navigation.home.HomeNavigation
import ru.bysoft.android.budget.common.navigation.settings.SettingsNavigation
import ru.bysoft.android.budget.common.navigation.splash.SplashNavigation
import ru.bysoft.android.budget.common.navigation.statistic.StatisticNavigation
import ru.bysoft.android.budget.common.navigation.transaction_detail.TransactionDetailNavigation
import ru.bysoft.android.budget.common.network.authentificator.ICommonNavigation
import ru.bysoft.android.budget.features.bottom_navigation.home.navigation.IHomeNavigation
import ru.bysoft.android.budget.features.bottom_navigation.statistic.navigation.IStatisticNavigation
import ru.bysoft.android.budget.features.create_udate_category.navigation.ICreateUpdateCategoryNavigation
import ru.bysoft.android.budget.features.create_update_delete_transactions.navigation.ITransactionNavigation
import ru.bysoft.android.budget.features.create_update_wallet.navigation.IWalletNavigation
import ru.bysoft.android.budget.features.settings.presentation.navigation.ISettingsNavigation
import ru.bysoft.android.budget.features.splash.navigation.ISplashNavigation
import ru.bysoft.android.budget.features.transaction_detail.navigation.ITransactionDetailNavigation

val NavigationDi = module {
    singleOf(::AuthNavigation) bind IAuthNavigation::class
    singleOf(::HomeNavigation) bind IHomeNavigation::class
    singleOf(::TransactionDetailNavigation) bind ITransactionDetailNavigation::class
    singleOf(::WalletNavigation) bind IWalletNavigation::class
    singleOf(::SplashNavigation) bind ISplashNavigation::class
    singleOf(::CommonNavigation) bind ICommonNavigation::class
    singleOf(::StatisticNavigation) bind IStatisticNavigation::class
    singleOf(::CreateUpdateCategoryNavigation) bind ICreateUpdateCategoryNavigation::class
    singleOf(::TransactionNavigation) bind ITransactionNavigation::class
    singleOf(::SettingsNavigation) bind ISettingsNavigation::class
}