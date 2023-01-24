package ru.bysoft.budget.features.bottom_navigation.statistic.data

import ru.bysoft.budget.features.bottom_navigation.statistic.data.entity.StatisticData
import ru.bysoft.budget.features.bottom_navigation.statistic.data.mapper.StatisticDataMapper
import ru.bysoft.budget.features.bottom_navigation.statistic.data.network.IStatisticApi
import javax.inject.Inject

interface IStatisticRepo {
    suspend fun getCategories(): StatisticData
}

class StatisticRepo @Inject constructor(
    private val api: IStatisticApi,
    private val mapper: StatisticDataMapper
) : IStatisticRepo {

    override suspend fun getCategories(): StatisticData {
        val response = api.getExpenses()
        return mapper.mapToData(response)
    }

}