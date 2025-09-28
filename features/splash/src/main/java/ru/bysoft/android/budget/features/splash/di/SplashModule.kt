package ru.bysoft.android.budget.features.splash.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.bysoft.android.budget.features.splash.SplashViewModel

val SplashDi = module {
    viewModelOf(::SplashViewModel)
}