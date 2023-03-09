package ru.bysoft.android.budget.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.ITransactionApi
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.ITransactionsCategoryApi
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.ITransactionsWalletApi
import ru.bysoft.android.budget.network.interceptors.AUTH_CLIENT_NAME
import ru.bysoft.android.budget.network.interceptors.MAIN_BASE_URL_NAME
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class TransactionsNetworkDi {
    @Provides
    fun provideTransactionsCategoryApi(
        @Named(MAIN_BASE_URL_NAME) baseUrl: String,
        @Named(AUTH_CLIENT_NAME) client: OkHttpClient
    ): ITransactionsCategoryApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ITransactionsCategoryApi::class.java)

    @Provides
    fun provideTransactionsWalletsApi(
        @Named(MAIN_BASE_URL_NAME) baseUrl: String,
        @Named(AUTH_CLIENT_NAME) client: OkHttpClient
    ): ITransactionsWalletApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ITransactionsWalletApi::class.java)

    @Provides
    fun provideTransactionsApi(
        @Named(MAIN_BASE_URL_NAME) baseUrl: String,
        @Named(AUTH_CLIENT_NAME) client: OkHttpClient
    ): ITransactionApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ITransactionApi::class.java)

}