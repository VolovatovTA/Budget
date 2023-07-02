package ru.budget.android.api.data.mapper

import ru.budget.android.api.data.source.network.entity.category.CategoryItemResponse
import ru.budget.android.api.data.source.network.entity.category.CategoryResponse
import ru.budget.android.api.data.source.network.entity.transactions.TransactionResponse
import ru.bysoft.android.budget.common.data_entity.*
import ru.bysoft.android.budget.common.network.entity.CommonErrorBody
import ru.bysoft.android.budget.common.util.PeriodState
import ru.bysoft.android.budget.common.util.restore
import ru.bysoft.android.budget.currency.getCurrency
import javax.inject.Inject

class CategoryDataMapper @Inject constructor() {
    fun mapToData(response: CategoryResponse): StatisticData {
        return StatisticData(
            listCategoryData = response.data.map { getCategoryData(it) }
        )
    }


    fun getNewCategoryData(
        transactionsResponse: TransactionResponse,
        oldCategoryData: ExpenseCategory
    ): ExpenseCategory {
        val amount = transactionsResponse.data?.map { transactionResponse ->
            if (transactionResponse?.currency != oldCategoryData.currency.iso4217) {
                transactionResponse?.exchanges?.firstOrNull { exchange ->
                    exchange.currency == oldCategoryData.currency.iso4217
                }?.amount?.toFloatOrNull() ?: 0f
            } else {
                transactionResponse.amount.toFloatOrNull() ?: 0f
            }
        }?.sum()
        return oldCategoryData.copy(
            amount = amount
        )
    }

    private fun getCategoryData(categoryExpense: CategoryItemResponse): ExpenseCategory =
        ExpenseCategory(
            currency = getCurrency(categoryExpense.currency),
            name = categoryExpense.name,
            iconName = categoryExpense.iconName,
            id = categoryExpense.id,
            limitAmount = categoryExpense.limitAmount?.toFloatOrNull(),
            limitType = getLimitType(categoryExpense.limitType)
        )

    private fun getLimitType(limitType: String?): PeriodState? {
        return when (limitType) {
            PeriodState.DAY.textToBack -> PeriodState.DAY
            PeriodState.WEEK.textToBack -> PeriodState.WEEK
            PeriodState.MONTH.textToBack -> PeriodState.MONTH
            PeriodState.PERIOD_DAYS.textToBack -> PeriodState.PERIOD_DAYS
            PeriodState.NO_PERIOD.textToBack -> PeriodState.NO_PERIOD
            else -> null
        }
    }

    fun getCategoryErrorType(e: String?): CategoryErrorType {
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
            CategoryErrorType.INVALID_CURRENCY.messageFromBack -> CategoryErrorType.INVALID_CURRENCY
            CategoryErrorType.INVALID_NAME.messageFromBack -> CategoryErrorType.INVALID_NAME
            CategoryErrorType.INVALID_ICON_NAME.messageFromBack -> CategoryErrorType.INVALID_ICON_NAME
            CategoryErrorType.NO_UNIQ_NAME.messageFromBack -> CategoryErrorType.NO_UNIQ_NAME
            else -> CategoryErrorType.UNKNOWN_ERROR
        }

}