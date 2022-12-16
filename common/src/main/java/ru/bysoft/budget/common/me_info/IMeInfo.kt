package ru.bysoft.budget.common.me_info

import android.util.Log
import ru.bysoft.budget.common.me_info.entity.MeData
import javax.inject.Inject

interface IMeInfo {
    fun setCurrentMeInfo(meInfoData: MeData)
    fun getCurrentMeInfo(): MeData?
}

class MeInfo @Inject constructor() : IMeInfo {
    val TAG = "Timofey"
    init {
        Log.d(TAG, "init")
    }
    private var cachedData: MeData? = null
    override fun setCurrentMeInfo(meInfoData: MeData) {
       cachedData = meInfoData
    }

    override fun getCurrentMeInfo(): MeData? = cachedData
}