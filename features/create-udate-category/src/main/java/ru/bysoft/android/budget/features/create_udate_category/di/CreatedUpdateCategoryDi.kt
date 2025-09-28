package ru.bysoft.android.budget.features.create_udate_category.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.bysoft.android.budget.features.create_udate_category.data.CategoryRepo
import ru.bysoft.android.budget.features.create_udate_category.data.ICategoryRepo

val CreatedUpdateCategoryDi = module {
    singleOf(::CategoryRepo) bind ICategoryRepo::class
}