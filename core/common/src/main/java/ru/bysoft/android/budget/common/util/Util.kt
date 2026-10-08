package ru.bysoft.android.budget.common.util

import android.content.Context
import android.os.Parcelable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import ru.bysoft.android.budget.common.R
import java.text.SimpleDateFormat
import java.util.*

const val TAG = "OkHttp"

fun Context.getStringFromAsset(filePath: String) =
    this.assets.open(filePath).bufferedReader().use { it.readText() }

inline fun <reified T> T.toJson(): String {
    return Json.encodeToString(this)
}

inline fun <reified T> String.restore() = Json.decodeFromString<T>(this)

const val pointJson = ".json"

fun String.applyFilter(): String {
    if (this == ".") return this
    val filtered = replace(',', '.').filter { it.isDigit() || it == '.' }
    when (val dotIndex = filtered.indexOf('.')) {
        -1 -> return filtered
        0 -> {
            val nextDotIndex = filtered.indexOf('.', 1)
            return if (nextDotIndex != -1) {
                // if we found anoter dot, we return substring from 0 to next dot
                "0" + filtered.substring(0, nextDotIndex)
            } else {
                // if we didn't find another dot, we return substring from 0 to last index adding the 0 to the beginning
                "0$filtered"
            }
        }

        filtered.lastIndex -> return filtered
        else -> {
            val nextDotIndex = filtered.indexOf('.', dotIndex + 1)
            return if (nextDotIndex != -1) {
                // if we found anoter dot, we return substring from 0 to next dot
                filtered.substring(0, nextDotIndex)
            } else {
                // if we didn't find another dot, we return filtered string
                filtered
            }
        }
    }
}

inline fun <R> R?.onNull(block: () -> R): R = this ?: block()

@Serializable
enum class PeriodState(val textToShow: Int, val textToBack: String) {
    @SerialName("DAY")
    DAY(R.string.add_text_per_day, "DAY"),

    @SerialName("WEEK")
    WEEK(R.string.add_text_per_week, "WEEK"),

    @SerialName("PERIOD_DAYS")
    PERIOD_DAYS(R.string.add_text_per_some_days, "PERIOD_DAYS"),

    @SerialName("MONTH")
    MONTH(R.string.add_text_per_mont, "MONTH"),

    @SerialName("NO_PERIOD")
    NO_PERIOD(R.string.add_text_whole_time, "WO_PERIOD");

    companion object {
        fun getByTextFromBack(text: String) = entries.firstOrNull { it.textToBack == text }
    }
}

@Serializable
enum class TransactionTypeEnum(
    @SerialName("text")
    val text: Int,
    @SerialName("nameForBack")
    val nameForBack: String
) {
    EXPENSE(R.string.btn_expense_text, "EXPENSE"),
    INCOME(R.string.btn_income_text, "INCOME"),
    TRANSFER(R.string.btn_transfer_text, "TRANSFER");
}

enum class CategoryTypeEnum(val text: Int, val pathToBack: String) {
    INCOME(R.string.btn_income_text, "incomes"), EXPENSE(R.string.btn_expense_text, "expenses");
}

const val dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
const val dateFormatOutput = "d MMMM HH:mm:ss"

/** Day of week the way the deprecated [Date.getDay] counted it: Sunday = 0 … Saturday = 6. */
fun Date.dayOfWeekSundayZero(): Int =
    Calendar.getInstance().apply { time = this@dayOfWeekSundayZero }.get(Calendar.DAY_OF_WEEK) - 1

fun getCalculatedDate(locale: Locale, days: Int): String? {
    val cal = Calendar.getInstance(locale)
    val s = SimpleDateFormat(dateFormat, locale)
    cal.add(Calendar.DAY_OF_YEAR, days)
    return s.format(Date(cal.timeInMillis))
}