package ru.bysoft.android.budget.features.bottom_navigation.statistic.data

import ru.budget.android.api.data.source.network.ICategoryApi
import ru.bysoft.android.budget.common.data_entity.StatisticData
import ru.budget.android.api.data.mapper.CategoryDataMapper
import ru.budget.android.api.data.source.network.ITransactionsApi
import ru.bysoft.android.budget.common.data_entity.ExpenseCategory
import ru.bysoft.android.budget.common.util.CategoryTypeEnum

interface IStatisticRepo {
    suspend fun getExpenses(): StatisticData
    suspend fun getUpdatedCategoryData(id: String, dateFrom: String?, dateTo: String?, oldCategoryData: ExpenseCategory): ExpenseCategory
}

class StatisticRepo(
    private val categoryApi: ICategoryApi,
    private val transactionApi: ITransactionsApi,
    private val mapper: CategoryDataMapper
) : IStatisticRepo {

    override suspend fun getExpenses(): StatisticData {
        val response = categoryApi.getCategories(CategoryTypeEnum.EXPENSE.pathToBack)
        return mapper.mapToData(response)
    }

    override suspend fun getUpdatedCategoryData(id: String, dateFrom: String?, dateTo: String?, oldCategoryData: ExpenseCategory): ExpenseCategory {
        val response = transactionApi.getExpensesTransactions(
            expenseIds = listOf(id),
            dateFrom = dateFrom,
            dateTo = dateTo
        )
        return mapper.getNewCategoryData(response, oldCategoryData)
    }

}