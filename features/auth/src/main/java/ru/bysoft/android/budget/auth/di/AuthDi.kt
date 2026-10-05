package ru.bysoft.android.budget.auth.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.bysoft.android.budget.auth.AuthViewModel
import ru.bysoft.android.budget.auth.IAuthViewModel
import ru.bysoft.android.budget.auth.data.AuthRepository
import ru.bysoft.android.budget.auth.data.IAuthRepository

val AuthDi = module {
    singleOf(::AuthRepository) bind IAuthRepository::class
    viewModelOf(::AuthViewModel) bind IAuthViewModel::class
}