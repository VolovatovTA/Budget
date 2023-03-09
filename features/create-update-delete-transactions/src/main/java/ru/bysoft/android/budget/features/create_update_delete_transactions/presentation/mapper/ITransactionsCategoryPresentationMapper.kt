package ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.mapper

import ru.bysoft.android.budget.common.util.getCurrency
import ru.bysoft.android.budget.features.create_update_delete_transactions.data.network.entity.responses.TransactionsCategoryResponse
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.CategoryPresentation
import ru.bysoft.android.budget.features.create_update_delete_transactions.presentation.entity.CategorySuccess
import javax.inject.Inject

interface ITransactionsCategoryPresentationMapper {
    fun toPresentation(response: TransactionsCategoryResponse): CategorySuccess
}

class TransactionsCategoryPresentationMapper @Inject constructor() :
    ITransactionsCategoryPresentationMapper {

    override fun toPresentation(response: TransactionsCategoryResponse): CategorySuccess {
        return CategorySuccess(
            listCategory = response.data.map {
                CategoryPresentation(
                    name = it.name,
                    iconName = it.iconName,
                    id = it.id,
                    currency = getCurrency(it.currency)?.displayName ?: "*"
                )
            }
        )
    }
}