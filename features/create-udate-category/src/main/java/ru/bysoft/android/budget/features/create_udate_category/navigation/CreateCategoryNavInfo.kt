package ru.bysoft.android.budget.features.create_udate_category.navigation

import kotlinx.serialization.Serializable
import ru.bysoft.android.budget.common.util.CategoryTypeEnum

@Serializable
data class CreateCategoryNavInfo(
    val typeCategory: CategoryTypeEnum
)
