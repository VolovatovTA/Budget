package ru.bysoft.android.budget.mock

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.budget.android.api.data.source.mock.AuthApiMock
import ru.budget.android.api.data.source.mock.CategoryApiMock
import ru.budget.android.api.data.source.mock.CurrencyRateApiMock
import ru.budget.android.api.data.source.mock.MeApiMock
import ru.budget.android.api.data.source.mock.TransactionApiMock
import ru.budget.android.api.data.source.mock.WalletApiMock
import ru.budget.android.api.data.source.network.IAuthApi
import ru.budget.android.api.data.source.network.ICategoryApi
import ru.budget.android.api.data.source.network.ICurrencyRatesApi
import ru.budget.android.api.data.source.network.IMeApi
import ru.budget.android.api.data.source.network.ITransactionsApi
import ru.budget.android.api.data.source.network.IWalletApi
import ru.bysoft.android.budget.common.network.MOCK_DELAY_NAME

val ApiDi = module {
    single<IWalletApi> {
        WalletApiMock(
            get(),
            get(named(MOCK_DELAY_NAME))
        )
    }
    single<ICategoryApi> { CategoryApiMock(get(), get(named(MOCK_DELAY_NAME))) }
    single<ITransactionsApi> {
        TransactionApiMock(
            get(),
            get(named(MOCK_DELAY_NAME))
        )
    }
    single<IAuthApi> {
        AuthApiMock(
            get(),
            get(named(MOCK_DELAY_NAME))
        )
    }
    single<IMeApi> {
        MeApiMock(
            get(),
            get(named(MOCK_DELAY_NAME))
        )
    }
    single<ICurrencyRatesApi> {
        CurrencyRateApiMock(
            get(),
        )
    }
}