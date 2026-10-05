package ru.bysoft.android.budget.common.me_info.entity

import ru.bysoft.android.budget.common.R
import java.util.*


data class MeData(
    val email: String,
    val name: String,
    val pictureUrl: String?,
    val settingsData: SettingsData?,
    val userId: String
)

data class SettingsData(
    val currency: String = "USD",
    val firstDayOfWeek: DayOfWeek,
)

enum class DayOfWeek(val value: Int, val resId: Int, val nameFromBack: String) {
    MONDAY(Calendar.MONDAY, R.string.monday, "MON"),
    TUESDAY(Calendar.TUESDAY, R.string.tuesday, "TUE"),
    WEDNESDAY(Calendar.WEDNESDAY, R.string.wednesday, "WED"),
    THURSDAY(Calendar.THURSDAY, R.string.thursday, "THU"),
    FRIDAY(Calendar.FRIDAY, R.string.friday, "FRI"),
    SATURDAY(Calendar.SATURDAY, R.string.saturday, "SAT"),
    SUNDAY(Calendar.SUNDAY, R.string.sunday, "SUN"),
}