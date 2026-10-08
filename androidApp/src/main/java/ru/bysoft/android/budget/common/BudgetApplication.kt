package ru.bysoft.android.budget.common

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.budget.android.api.di.ResponseToDataMappersDi
import ru.bysoft.android.budget.auth.di.AuthDi
import ru.bysoft.android.budget.common.di.CommonDi
import ru.bysoft.android.budget.common.di.NavigationDi
import ru.bysoft.android.budget.common.navigation.NavHostControllerWrapper
import ru.bysoft.android.budget.common.token.di.TokenDi
import ru.bysoft.android.budget.features.bottom_navigation.home.di.HomeDi
import ru.bysoft.android.budget.features.bottom_navigation.statistic.di.StatisticDi
import ru.bysoft.android.budget.features.create_udate_category.di.CreatedUpdateCategoryDi
import ru.bysoft.android.budget.features.create_update_delete_transactions.di.TransactionsDi
import ru.bysoft.android.budget.features.create_update_wallet.di.CreateWalletDi
import ru.bysoft.android.budget.features.currency_rates.di.CurrencyRatedDi
import ru.bysoft.android.budget.features.settings.presentation.SettingDi
import ru.bysoft.android.budget.features.splash.di.SplashDi
import ru.bysoft.android.budget.features.statistic_by_month.di.StatisticByFiltersDi
import ru.bysoft.android.budget.di.buildTypeModules
import ru.bysoft.android.budget.di.flavorModules
import ru.bysoft.shared.Platform

class BudgetApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BudgetApplication)
            androidLogger()
            modules(appModules)
        }
    }
}

val platformModule = module {
    singleOf(::Platform)
    singleOf(::NavHostControllerWrapper)
}

val appModules = listOf(
    platformModule,
    SplashDi,
    TokenDi,
    CurrencyRatedDi,
    AuthDi,
    HomeDi,
    StatisticDi,
    CreatedUpdateCategoryDi,
    TransactionsDi,
    CreateWalletDi,
    SettingDi,
    StatisticByFiltersDi,
    NavigationDi,
    CommonDi,
    ResponseToDataMappersDi
) + flavorModules + buildTypeModules