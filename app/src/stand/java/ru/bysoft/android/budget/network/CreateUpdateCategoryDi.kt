package ru.bysoft.android.budget.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.bysoft.android.budget.features.create_udate_category.data.network.ICategoryApi
import ru.bysoft.android.budget.network.interceptors.AUTH_CLIENT_NAME
import ru.bysoft.android.budget.network.interceptors.MAIN_BASE_URL_NAME
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class CreateUpdateCategoryDi {

    @Provides
    fun provideCreateWalletApi(
        @Named(MAIN_BASE_URL_NAME) baseUrl: String,
        @Named(AUTH_CLIENT_NAME) client: OkHttpClient
    ): ICategoryApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ICategoryApi::class.java)
}