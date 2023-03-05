package ru.bysoft.budget.features.bottom_navigation.statistic.data

import ru.bysoft.budget.features.bottom_navigation.statistic.data.entity.CategoryData
import ru.bysoft.budget.features.bottom_navigation.statistic.data.entity.StatisticData
import ru.bysoft.budget.features.bottom_navigation.statistic.data.mapper.StatisticDataMapper
import ru.bysoft.budget.features.bottom_navigation.statistic.data.network.IStatisticApi
import javax.inject.Inject

interface IStatisticRepo {
    suspend fun getCategories(): StatisticData
    suspend fun getUpdatedCategoryData(id: String, dateFrom: String?, dateTo: String?, oldCategoryData: CategoryData): CategoryData
}

class StatisticRepo @Inject constructor(
    private val api: IStatisticApi,
    private val mapper: StatisticDataMapper
) : IStatisticRepo {

    override suspend fun getCategories(): StatisticData {
        val response = api.getExpenses()
        return mapper.mapToData(response)
    }

    override suspend fun getUpdatedCategoryData(id: String, dateFrom: String?, dateTo: String?, oldCategoryData: CategoryData): CategoryData {
        val response = api.getExpensesTransactions(
            expenseIds = listOf(id),
            dateFrom = dateFrom,
            dateTo = dateTo
        )
        return mapper.getNewCategoryData(response, oldCategoryData)
    }

}