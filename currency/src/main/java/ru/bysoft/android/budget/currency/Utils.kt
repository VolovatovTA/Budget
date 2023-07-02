package ru.bysoft.android.budget.currency

import java.util.*

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
    listCurrency.firstOrNull { it.iso4217 == iso4217 } ?: BudgetCurrency.Unkcnown

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
//TODO: replace BudgetCurrency with BudgetCurrencyEnum
enum class BudgetCurrencyEnum(
    val displayName: String,
    val iso4217: String,
    val flag: Int
) {
    AFN("؋", "AFN", R.drawable.af_afghanistan),
    THB("฿", "THB", R.drawable.th_thailand),
    KPW("₩", "KPW", R.drawable.kp_north_korea),
    UAH("₴", "UAH", R.drawable.ua_ukraine),
    PYG("₲", "PYG", R.drawable.py_paraguay),
    ANG("ƒ", "ANG", R.drawable.nl_netherlands),
    VND("₫", "VND", R.drawable.vn_vietnam),
    EUR("€", "EUR", R.drawable.eu_europe_big2),
    USD("$", "USD", R.drawable.us_united_states_of_america_usa),
    JPY("¥", "JPY", R.drawable.jp_japan),
    LAK("₭", "LAK", R.drawable.la_laos),
    CRC("₡", "CRC", R.drawable.cr_costa_rica),
    GEL("₾", "GEL", R.drawable.ge_georgia),
    TRY("₺", "TRY", R.drawable.tr_turkey),
    AZN("₼", "AZN", R.drawable.az_azerbaijan),
    NGN("₦", "NGN", R.drawable.ng_nigeria),
    IRR("﷼", "IRR", R.drawable.ir_iran),
    KHR("៛", "KHR", R.drawable.kh_cambodia),
    RUB("₽", "RUB", R.drawable.ru_russia),
    BGN("лв", "BGN", R.drawable.bg_bulgaria),
    RSD("дин", "RSD", R.drawable.rs_serbia),
    SEK("кр", "SEK", R.drawable.se_sweden),
    RUR("₽", "RUR", R.drawable.ru_russia),
    INR("₹", "INR", R.drawable.in_india),
    GHS("₵", "GHS", R.drawable.gh_ghana),
    BDT("৳", "BDT", R.drawable.bd_bangladesh),
    KZT("₸", "KZT", R.drawable.kz_kazakhstan),
    MNT("₮", "MNT", R.drawable.mn_mongolia),
    AWG("ƒ", "AWG", R.drawable.aw_aruba),
    HUF("ƒ", "HUF", R.drawable.hu_hungary),
    GBP("₤", "GBP", R.drawable.gb_eng_england),
    GIP("₤", "GIP", R.drawable.gi_gibraltar),
    EGP("₤", "EGP", R.drawable.eg_egypt),
    LBP("₤", "LBP", R.drawable.lb_lebanon),
    SHP("₤", "SHP", R.drawable.sh_saint_helena_ascension_and_tristan_da_cunha),
    FKP("₤", "FKP", R.drawable.fk_falkland_islands),
    SYP("₤", "SYP", R.drawable.sy_syria),
    SDG("₤", "SDG", R.drawable.sd_sudan),
    AMD("֏", "AMD", R.drawable.am_armenia),
    UNKNOWN("", "", R.drawable.aw_aruba);
}

fun getBeautifulAmount(amount: Float, currency: BudgetCurrency): String {
    if (currency == BudgetCurrency.Unkcnown) return amount.toString()
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

val currencyWithFlags = listCurrency.map {
    when (it.iso4217) {
        BudgetCurrencyEnum.RSD.iso4217 -> it to R.drawable.rs_serbia
        BudgetCurrencyEnum.EUR.iso4217 -> it to R.drawable.eu_europe_big2
        BudgetCurrencyEnum.GEL.iso4217 -> it to R.drawable.ge_georgia
        BudgetCurrencyEnum.RUB.iso4217 -> it to R.drawable.ru_russia
        BudgetCurrencyEnum.KZT.iso4217 -> it to R.drawable.kz_kazakhstan
        BudgetCurrencyEnum.AMD.iso4217 -> it to R.drawable.am_armenia
        BudgetCurrencyEnum.USD.iso4217 -> it to R.drawable.us_united_states_of_america_usa
        else -> it to R.drawable.cc_cocos_keeling_islands
    }
}