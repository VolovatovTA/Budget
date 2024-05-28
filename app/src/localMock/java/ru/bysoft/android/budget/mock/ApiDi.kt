package ru.bysoft.android.budget.mock

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.budget.android.api.data.source.mock.*
import ru.budget.android.api.data.source.network.*

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiDi {
    @Binds
    abstract fun bindWalletApiMock(mockImpl: WalletApiMock): IWalletApi

    @Binds
    abstract fun bindCategoryApiMock(mockImpl: CategoryApiMock): ICategoryApi

    @Binds
    abstract fun bindTransactionApiMock(mockImpl: TransactionApiMock): ITransactionsApi

    @Binds
    abstract fun bindAuthApiMock(mockImpl: AuthApiMock): IAuthApi

    @Binds
    abstract fun bindMeApiMock(mockImpl: MeApiMock): IMeApi

    @Binds
    abstract fun bindCurrencyRatesApiMock(mockImpl: CurrencyRateApiMock): ICurrencyRatesApi
}