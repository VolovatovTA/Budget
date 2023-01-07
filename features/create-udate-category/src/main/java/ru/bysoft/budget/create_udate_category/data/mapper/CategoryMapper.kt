package ru.bysoft.budget.create_udate_category.data.mapper

import ru.bysoft.budget.common.network.entity.CommonErrorBody
import ru.bysoft.budget.common.util.restore
import ru.bysoft.budget.create_udate_category.data.entity.CategoryErrorType
import javax.inject.Inject

interface ICategoryMapper {
    fun getCategoryErrorType(e: String?): CategoryErrorType
}

class CategoryMapper @Inject constructor() : ICategoryMapper {
    override fun getCategoryErrorType(e: String?): CategoryErrorType {
        if (e == null) return CategoryErrorType.NULL_ERROR
        val restoredObject = e.restore<CommonErrorBody>()
        return if (restoredObject is CommonErrorBody) {
            getErrorTypeByString(restoredObject.slug)
        } else {
            // что-то не так со слагом
            CategoryErrorType.TECHNICAL_ERROR_IN_BACK
        }

    }

    private fun getErrorTypeByString(slug: String): CategoryErrorType =
        when (slug) {
            CategoryErrorType.INVALID_CURRENCY_.messageFromBack -> CategoryErrorType.INVALID_CURRENCY_
            CategoryErrorType.INVALID_NAME_____.messageFromBack -> CategoryErrorType.INVALID_NAME_____
            CategoryErrorType.INVALID_ICON_NAME.messageFromBack -> CategoryErrorType.INVALID_ICON_NAME
            CategoryErrorType.NO_UNIQ_NAME_____.messageFromBack -> CategoryErrorType.NO_UNIQ_NAME_____
            else -> CategoryErrorType.UNKNOWN_ERROR
        }

}