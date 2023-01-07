package ru.bysoft.budget.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.bysoft.budget.features.bottom_navigation.home.data.me.network.IHomeMeApi
import ru.bysoft.budget.features.bottom_navigation.home.data.transactions.network.ITransactionsApi
import ru.bysoft.budget.features.bottom_navigation.home.data.wallets.network.IHomeWalletsApi
import ru.bysoft.budget.features.bottom_navigation.statistic.data.network.StatisticApi
import ru.bysoft.budget.network.interceptors.AUTH_CLIENT_NAME
import ru.bysoft.budget.network.interceptors.MAIN_BASE_URL_NAME
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class StatisticNetworkDi {

    companion object {
        @Provides
        fun provideStatisticApi(
            @Named(MAIN_BASE_URL_NAME) baseUrl: String,
            @Named(AUTH_CLIENT_NAME) client: OkHttpClient
        ): StatisticApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StatisticApi::class.java)
    }

}