package ru.bysoft.android.budget.common.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.bysoft.android.budget.common.errors.ErrorLogger
import ru.bysoft.android.budget.common.errors.IErrorLogger
import ru.bysoft.android.budget.common.me_info.IMeInfo
import ru.bysoft.android.budget.common.me_info.MeInfo
import java.util.Locale

val CommonDi = module {
    singleOf(::MeInfo) bind IMeInfo::class
    singleOf(::ErrorLogger) bind IErrorLogger::class
    single { Locale.getDefault() }
}