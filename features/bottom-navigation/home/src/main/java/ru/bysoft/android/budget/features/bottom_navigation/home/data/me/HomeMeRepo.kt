package ru.bysoft.android.budget.features.bottom_navigation.home.data.me

import android.os.Build
import ru.budget.android.api.data.source.network.IMeApi
import ru.bysoft.android.budget.features.bottom_navigation.home.data.me.mapper.mapToData
import ru.bysoft.android.budget.common.me_info.entity.MeData

interface IHomeMeRepo {
    suspend fun getMeInfo(): MeData
}

class HomeMeRepo(
    private val api: IMeApi
) : IHomeMeRepo {

    override suspend fun getMeInfo(): MeData =
        mapToData(api.getMeInfo())

}