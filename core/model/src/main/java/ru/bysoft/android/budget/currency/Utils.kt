package ru.bysoft.android.budget.currency

import java.util.*

private val listCurrency = listOf(
    BudgetCurrencyEnum.EUR,
    BudgetCurrencyEnum.GEL,
    BudgetCurrencyEnum.RUB,
    BudgetCurrencyEnum.RSD,
    BudgetCurrencyEnum.KZT,
    BudgetCurrencyEnum.AMD,
    BudgetCurrencyEnum.USD
)

fun getCurrency(iso4217: String?): BudgetCurrencyEnum =
    listCurrency.firstOrNull { it.iso4217 == iso4217 } ?: BudgetCurrencyEnum.UNKNOWN

fun getCurrencyByDisplayName(displayName: String?): BudgetCurrencyEnum? =
    listCurrency.firstOrNull { it.displayName == displayName }

fun getAvailableCurrency() = listCurrency

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

fun getBeautifulAmount(amount: Float, currency: BudgetCurrencyEnum): String {
    if (currency == BudgetCurrencyEnum.UNKNOWN) return amount.toString()
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