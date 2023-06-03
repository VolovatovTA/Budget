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

fun getBeautifulAmount(amount: Float, currency: BudgetCurrency): String {
    val countNumbersAfterDot = Currency.getInstance(currency.iso4217).defaultFractionDigits
    if (countNumbersAfterDot == 0) return "${amount.toInt()} ${currency.displayName}"
    val roundedAmount = String.format("%.${countNumbersAfterDot}f", amount).replace(',', '.')
    val accurateAmount = roundedAmount
        .dropLastWhile { it != '.' }
        .dropLast(1)
        .reversed()
        .chunked(3)
        .takeIf { it.isNotEmpty() }
        ?.reduce { acc, s -> "$acc $s" }
        ?.reversed() ?: "0"
    val decimals = roundedAmount.dropWhile { it != '.' }.drop(1)
    return "$accurateAmount.$decimals ${currency.displayName}"

}

const val pointJson = ".json"

private val listCurrency = listOf(
//    BudgetCurrency("؋", "AFN"),
//    BudgetCurrency("฿", "THB"),
//    BudgetCurrency("₩", "KPW"),
//    BudgetCurrency("₴", "UAH"),
//    BudgetCurrency("₲", "PYG"),
//    BudgetCurrency("ƒ", "ANG"),
//    BudgetCurrency("₫", "VND"),
    BudgetCurrency("€", "EUR"),
//    BudgetCurrency("¥", "JPY"),
//    BudgetCurrency("₭", "LAK"),
//    BudgetCurrency("₡", "CRC"),
    BudgetCurrency("₾", "GEL"),
//    BudgetCurrency("₺", "TRY"),
//    BudgetCurrency("₼", "AZN"),
//    BudgetCurrency("₦", "NGN"),
//    BudgetCurrency("﷼", "IRR"),
//    BudgetCurrency("៛", "KHR"),
    BudgetCurrency("₽", "RUB"),
//    BudgetCurrency("лв", "BGN"),
    BudgetCurrency("дин", "RSD"),
//    BudgetCurrency("кр", "SEK"),
//    BudgetCurrency("₽", "RUR"),
//    BudgetCurrency("₹", "INR"),
//    BudgetCurrency("₵", "GHS"),
//    BudgetCurrency("৳", "BDT"),
    BudgetCurrency("₸", "KZT"),
//    BudgetCurrency("₮", "MNT"),
//    BudgetCurrency("ƒ", "AWG"),
//    BudgetCurrency("ƒ", "HUF"),
//    BudgetCurrency("₤", "GBP"),
//    BudgetCurrency("₤", "GIP"),
//    BudgetCurrency("₤", "EGP"),
//    BudgetCurrency("₤", "LBP"),
//    BudgetCurrency("₤", "SHP"),
//    BudgetCurrency("₤", "SYP"),
//    BudgetCurrency("₤", "SDG"),
//    BudgetCurrency("₤", "FKP"),
//    BudgetCurrency("₪", "ILS"),
//    BudgetCurrency("¥", "CNY"),
    BudgetCurrency("֏", "AMD"),
    BudgetCurrency("$", "USD"),
//    BudgetCurrency("₣", "CHF"),
)

fun getCurrency(iso4217: String?): BudgetCurrency =
    listCurrency.firstOrNull { it.iso4217 == iso4217 }?: BudgetCurrency.Unkcnown

fun getCurrencyByDisplayName(displayName: String?): BudgetCurrency? =
    listCurrency.firstOrNull { it.displayName == displayName }

fun getAvailableCurrency() = listCurrency
data class BudgetCurrency(
    val displayName: String,
    val iso4217: String
) {
    companion object {
        val Unkcnown = BudgetCurrency("", "")
    }
}

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

enum class TransactionTypeEnum(val text: Int) {
    EXPENSE(R.string.btn_expense_text), INCOME(R.string.btn_income_text), TRANSFER(R.string.btn_transfer_text);
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