package ru.bysoft.android.budget.common.me_info

import ru.bysoft.android.budget.common.me_info.entity.MeData

interface IMeInfo {
    fun setCurrentMeInfo(meInfoData: MeData)
    fun getCurrentMeInfo(): MeData?
}

class MeInfo : IMeInfo {
    private var cachedData: MeData? = null
    override fun setCurrentMeInfo(meInfoData: MeData) {
       cachedData = meInfoData
    }

    override fun getCurrentMeInfo(): MeData? = cachedData
}