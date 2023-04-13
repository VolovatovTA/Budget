package ru.bysoft.android.budget.features.bottom_navigation.home.data.me

import ru.bysoft.android.budget.features.bottom_navigation.home.data.me.mapper.mapToData
import ru.bysoft.android.budget.features.bottom_navigation.home.data.me.network.IHomeMeApi
import ru.bysoft.android.budget.common.me_info.entity.MeData
import javax.inject.Inject

interface IHomeMeRepo {
    suspend fun getMeInfo(): MeData
}

class HomeMeRepo @Inject constructor(
    private val api: IHomeMeApi
) : IHomeMeRepo {

    override suspend fun getMeInfo(): MeData =
        mapToData(api.getMeInfo())

}