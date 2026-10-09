package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper

import ru.bysoft.android.budget.common.data_entity.CategoryData
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.CategoryPresentation
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.CategorySuccess

interface ITransactionsCategoryPresentationMapper {
    fun toPresentation(categories: List<CategoryData>): CategorySuccess
}

class TransactionsCategoryPresentationMapper : ITransactionsCategoryPresentationMapper {
    override fun toPresentation(categories: List<CategoryData>): CategorySuccess =
        CategorySuccess(
            listCategory = categories.map {
                CategoryPresentation(
                    name = it.name,
                    iconName = it.iconName,
                    id = it.id,
                    currency = it.currency.displayName,
                )
            }
        )
}
