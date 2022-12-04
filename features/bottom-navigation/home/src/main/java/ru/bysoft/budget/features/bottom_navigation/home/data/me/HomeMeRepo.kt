package ru.bysoft.budget.features.bottom_navigation.home.data.me

import ru.bysoft.budget.features.bottom_navigation.home.data.me.mapper.mapToData
import ru.bysoft.budget.features.bottom_navigation.home.data.me.network.IHomeMeApi
import ru.bysoft.budget.features.bottom_navigation.home.data.me.entity.MeData
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