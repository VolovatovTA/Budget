package ru.bysoft.android.budget.common.util

import android.content.Context
import com.google.gson.Gson
import ru.bysoft.android.budget.common.R
import java.text.SimpleDateFormat
import java.util.*

const val TAG = "OkHttp"

fun Context.getStringFromAsset(filePath: String) =
    this.assets.open(filePath).bufferedReader().use { it.readText() }

fun <T> T.toJson() = Gson().toJson(this)
inline fun <reified T> String.restore() = Gson().fromJson(this, T::class.java)

const val pointJson = ".json"



inline fun <R> R?.onNull(block: () -> R): R = this ?: block()

enum class PeriodState(val textToShow: Int, val textToBack: String) {
    DAY(R.string.add_text_per_day, "DAY"),
    WEEK(R.string.add_text_per_week, "WEEK"),
    PERIOD_DAYS(R.string.add_text_per_some_days, "PERIOD_DAYS"),
    MONTH(R.string.add_text_per_mont, "MONTH"),
    NO_PERIOD(R.string.add_text_whole_time, "WO_PERIOD");

    companion object {
        fun getByTextFromBack(text: String) = values().firstOrNull { it.textToBack == text }
    }
}

enum class TransactionTypeEnum(val text: Int, val nameForBack: String) {
    EXPENSE(R.string.btn_expense_text, "EXPENSE"), INCOME(R.string.btn_income_text, "INCOME"), TRANSFER(R.string.btn_transfer_text, "TRANSFER");
}

enum class CategoryTypeEnum(val text: Int, val pathToBack: String) {
    INCOME(R.string.btn_income_text, "incomes"), EXPENSE(R.string.btn_expense_text, "expenses");
}

const val dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
const val dateFormatOutput = "d MMMM HH:mm:ss"

fun getCalculatedDate(locale: Locale, days: Int): String? {
    val cal = Calendar.getInstance(locale)
    val s = SimpleDateFormat(dateFormat, locale)
    cal.add(Calendar.DAY_OF_YEAR, days)
    return s.format(Date(cal.timeInMillis))
}