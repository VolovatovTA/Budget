package ru.bysoft.budget.features.bottom_navigation.statistic.data.mapper

import android.util.Log
import ru.bysoft.budget.common.util.PeriodState
import ru.bysoft.budget.common.util.TAG
import ru.bysoft.budget.common.util.getCurrency
import ru.bysoft.budget.features.bottom_navigation.statistic.data.entity.CategoryData
import ru.bysoft.budget.features.bottom_navigation.statistic.data.entity.StatisticData
import ru.bysoft.budget.features.bottom_navigation.statistic.data.network.entity.CategoryExpenseResponse
import ru.bysoft.budget.features.bottom_navigation.statistic.data.network.entity.ListTransactionsResponse
import ru.bysoft.budget.features.bottom_navigation.statistic.data.network.entity.StatisticExpenseResponse
import javax.inject.Inject

class StatisticDataMapper @Inject constructor() {
    fun mapToData(response: StatisticExpenseResponse): StatisticData {
        return StatisticData(
            listCategoryData = response.data.map { getCategoryData(it) }
        )
    }


    fun getNewCategoryData(
        transactionsResponse: ListTransactionsResponse,
        oldCategoryData: CategoryData
    ): CategoryData {
        val amount = transactionsResponse.data.map { transactionResponse ->
            if (transactionResponse.currency != oldCategoryData.currency.iso4217) {
                transactionResponse.exchanges.firstOrNull { exchange ->
                    exchange.currency == oldCategoryData.currency.iso4217
                }?.amount?.toFloatOrNull() ?: 0f
            } else {
                transactionResponse.amount
            }
        }.sum()
        return oldCategoryData.copy(
            amount = amount
        )
    }

    private fun getCategoryData(categoryExpense: CategoryExpenseResponse): CategoryData =
        CategoryData(
            currency = getCurrency(categoryExpense.currency)!!,
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
}