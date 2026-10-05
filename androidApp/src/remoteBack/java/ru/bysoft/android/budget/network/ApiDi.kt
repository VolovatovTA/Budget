package ru.bysoft.android.budget.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.budget.android.api.data.source.network.*
import ru.bysoft.android.budget.BuildConfig
import ru.bysoft.android.budget.network.interceptors.AUTH_CLIENT_NAME
import ru.bysoft.android.budget.network.interceptors.MAIN_BASE_URL_NAME
import ru.bysoft.android.budget.network.interceptors.NO_AUTH_CLIENT_NAME
import ru.bysoft.android.budget.network.interceptors.WALLET_BASE_URL_NAME
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class ApiDi {

    @Provides
    fun provideCategoryApi(
        @Named(WALLET_BASE_URL_NAME) baseUrl: String,
        @Named(AUTH_CLIENT_NAME) client: OkHttpClient
    ): ICategoryApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ICategoryApi::class.java)
    @Provides
    fun provideWalletApi(
        @Named(WALLET_BASE_URL_NAME) baseUrl: String,
        @Named(AUTH_CLIENT_NAME) client: OkHttpClient
    ): IWalletApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(IWalletApi::class.java)


    @Provides
    fun provideTransactionsApi(
        @Named(WALLET_BASE_URL_NAME) baseUrl: String,
        @Named(AUTH_CLIENT_NAME) client: OkHttpClient
    ): ITransactionsApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ITransactionsApi::class.java)

    @Provides
    fun provideAuthApi(
        @Named(MAIN_BASE_URL_NAME) baseUrl: String,
        @Named(NO_AUTH_CLIENT_NAME) client: OkHttpClient
    ): IAuthApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(IAuthApi::class.java)

    @Provides
    fun provideHomeMeApi(
        @Named(MAIN_BASE_URL_NAME) baseUrl: String,
        @Named(AUTH_CLIENT_NAME) client: OkHttpClient
    ): IMeApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(IMeApi::class.java)

    @Provides
    fun provideCurrencyRatesApi(
        @Named(AUTH_CLIENT_NAME) client: OkHttpClient
    ): ICurrencyRatesApi = Retrofit.Builder()
            .baseUrl("https://v6.exchangerate-api.com/v6/${BuildConfig.EXCHANGE_RATE_API_KEY}/latest/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ICurrencyRatesApi::class.java)
}