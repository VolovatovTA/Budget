package ru.bysoft.android.budget.common.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.bysoft.android.budget.common.me_info.IMeInfo
import ru.bysoft.android.budget.common.me_info.MeInfo
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonDi {
    @Binds
    @Singleton
    abstract fun bindMeInfo(meInfo: MeInfo): IMeInfo


    companion object {
        @Provides
        fun provideLocale(
            @ApplicationContext context: Context
        ): Locale {
            return context.resources.configuration.locale
        }
    }
}