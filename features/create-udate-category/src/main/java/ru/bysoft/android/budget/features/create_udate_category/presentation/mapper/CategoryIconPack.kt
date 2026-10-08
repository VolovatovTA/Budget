package ru.bysoft.android.budget.features.create_udate_category.presentation.mapper

import ru.bysoft.android.budget.common.util.CategoryTypeEnum
import ru.bysoft.android.budget.uikit.icons.UiKitIcons.UiKitIconPack

/** Picks the icon set the picker offers for a category of this type. */
fun CategoryTypeEnum.toIconPack(): UiKitIconPack = when (this) {
    CategoryTypeEnum.EXPENSE -> UiKitIconPack.EXPENSE
    CategoryTypeEnum.INCOME -> UiKitIconPack.INCOME
}
