package ru.bysoft.budget.home.data.me

import ru.bysoft.budget.home.data.me.entity.MeData
import ru.bysoft.budget.home.data.me.mapper.mapToData
import ru.bysoft.budget.home.data.me.network.IHomeMeApi
import javax.inject.Inject

interface IHomeMeRepo {
    suspend fun getMeInfo(): MeData
}

class HomeMeRepo @Inject constructor(
    private val api: IHomeMeApi
) : IHomeMeRepo {

    override suspend fun getMeInfo(): MeData =
        api.getMeInfo().mapToData()

}