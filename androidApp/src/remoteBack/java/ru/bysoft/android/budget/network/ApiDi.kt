package ru.bysoft.android.budget.network

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.budget.android.api.data.source.network.IAuthApi
import ru.budget.android.api.data.source.network.ICategoryApi
import ru.budget.android.api.data.source.network.ICurrencyRatesApi
import ru.budget.android.api.data.source.network.IMeApi
import ru.budget.android.api.data.source.network.ITransactionsApi
import ru.budget.android.api.data.source.network.IWalletApi
import ru.bysoft.android.budget.BuildConfig

private const val EXCHANGE_RATE_BASE_URL = "https://v6.exchangerate-api.com/v6/${BuildConfig.EXCHANGE_RATE_API_KEY}/latest/"

val ApiDi = module {
    single<ICategoryApi> { retrofit(get(named(WALLET_BASE_URL_NAME)), get(named(AUTH_CLIENT_NAME))) }
    single<IWalletApi> { retrofit(get(named(WALLET_BASE_URL_NAME)), get(named(AUTH_CLIENT_NAME))) }
    single<ITransactionsApi> { retrofit(get(named(WALLET_BASE_URL_NAME)), get(named(AUTH_CLIENT_NAME))) }
    single<IAuthApi> { retrofit(get(named(MAIN_BASE_URL_NAME)), get(named(NO_AUTH_CLIENT_NAME))) }
    single<IMeApi> { retrofit(get(named(MAIN_BASE_URL_NAME)), get(named(AUTH_CLIENT_NAME))) }
    single<ICurrencyRatesApi> { retrofit(EXCHANGE_RATE_BASE_URL, get(named(AUTH_CLIENT_NAME))) }
}
