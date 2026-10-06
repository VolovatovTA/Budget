package ru.bysoft.android.budget.features.create_udate_category.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.bysoft.android.budget.features.create_udate_category.data.CategoryRepo
import ru.bysoft.android.budget.features.create_udate_category.data.ICategoryRepo
import ru.bysoft.android.budget.features.create_udate_category.presentation.viewmodels.CreateCategoryViewModel
import ru.bysoft.android.budget.features.create_udate_category.presentation.viewmodels.UpdateCategoryViewModel

val CreatedUpdateCategoryDi = module {
    singleOf(::CategoryRepo) bind ICategoryRepo::class
    viewModelOf(::CreateCategoryViewModel)
    viewModelOf(::UpdateCategoryViewModel)
}