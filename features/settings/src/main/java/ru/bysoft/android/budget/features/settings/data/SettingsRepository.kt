package ru.bysoft.android.budget.features.settings.data

import ru.budget.android.api.data.source.network.IMeApi
import ru.budget.android.api.data.source.network.entity.me.SettingsRequest
import ru.bysoft.android.budget.common.me_info.entity.DayOfWeek
import ru.bysoft.android.budget.common.util.BudgetCurrency
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val api: IMeApi
) {

    suspend fun setMeInfo(currency: BudgetCurrency?, firstDayOfWeek: DayOfWeek?, pictureUrl: String?) {
        api.setMeInfo(
            SettingsRequest(
                currencyRequest = currency?.iso4217,
                firstDayOfWeek = firstDayOfWeek?.nameFromBack,
                pictureUrl = pictureUrl
            )
        )
    }
}