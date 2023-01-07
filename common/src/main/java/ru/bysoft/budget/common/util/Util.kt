package ru.bysoft.budget.common.util

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import java.util.*

val TAG = "Timofey"

fun Context.getStringFromAsset(filePath: String) =
    this.assets.open(filePath).bufferedReader().use { it.readText() }

fun <T> T.toJson() = Gson().toJson(this)
inline fun <reified T> String.restore() = Gson().fromJson(this, T::class.java)

fun getBeautifulAmount(amount: Float, currency: BudgetCurrency): String {
    val countNumbersAfterDot = Currency.getInstance(currency.iso4217).defaultFractionDigits
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
    BudgetCurrency('؋', "AFN"),
    BudgetCurrency('฿', "THB"),
    BudgetCurrency('₩', "KPW"),
    BudgetCurrency('₴', "UAH"),
    BudgetCurrency('₲', "PYG"),
    BudgetCurrency('ƒ', "ANG"),
    BudgetCurrency('₫', "VND"),
    BudgetCurrency('€', "EUR"),
    BudgetCurrency('¥', "JPY"),
    BudgetCurrency('₭', "LAK"),
    BudgetCurrency('₡', "CRC"),
    BudgetCurrency('₾', "GEL"),
    BudgetCurrency('₺', "TRY"),
    BudgetCurrency('₼', "AZN"),
    BudgetCurrency('₦', "NGN"),
    BudgetCurrency('﷼', "IRR"),
    BudgetCurrency('៛', "KHR"),
    BudgetCurrency('₽', "RUB"),
    BudgetCurrency('₽', "RUR"),
    BudgetCurrency('₹', "INR"),
    BudgetCurrency('₵', "GHS"),
    BudgetCurrency('৳', "BDT"),
    BudgetCurrency('₸', "KZT"),
    BudgetCurrency('₮', "MNT"),
    BudgetCurrency('ƒ', "AWG"),
    BudgetCurrency('ƒ', "HUF"),
    BudgetCurrency('₤', "GBP"),
    BudgetCurrency('₤', "GIP"),
    BudgetCurrency('₤', "EGP"),
    BudgetCurrency('₤', "LBP"),
    BudgetCurrency('₤', "SHP"),
    BudgetCurrency('₤', "SYP"),
    BudgetCurrency('₤', "SDG"),
    BudgetCurrency('₤', "FKP"),
    BudgetCurrency('₪', "ILS"),
    BudgetCurrency('¥', "CNY"),
    BudgetCurrency('֏', "AMD"),
    BudgetCurrency('$', "USD"),
)

fun getCurrency(iso4217: String?): BudgetCurrency? =
    listCurrency.firstOrNull { it.iso4217 == iso4217 }

fun getAvailableCurrency() = listCurrency
data class BudgetCurrency(
    val displayName: Char,
    val iso4217: String
)

inline fun <R> R?.onNull(block: () -> R): R = this ?: block()