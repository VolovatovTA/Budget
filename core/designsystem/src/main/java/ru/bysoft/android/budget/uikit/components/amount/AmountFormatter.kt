package ru.bysoft.android.budget.uikit.components.amount

import java.text.DecimalFormatSymbols
import java.util.Locale

/**
 * Pure text rules of the amount field. The raw value is digits with an optional `.` fraction of at
 * most [MAX_FRACTION] digits ("1234.5"); [format] turns it into the locale's display text and
 * [parse] turns whatever the user typed or pasted back into a raw value.
 */
internal class AmountFormatter(locale: Locale) {

    val decimalSeparator: Char
    val groupingSeparator: Char

    init {
        val symbols = DecimalFormatSymbols.getInstance(locale)
        decimalSeparator = symbols.decimalSeparator
        // every space-like grouping mark (ru, fr, ...) is shown as one thin space
        val grouping = symbols.groupingSeparator
        groupingSeparator = if (grouping.isWhitespace()) THIN_SPACE else grouping
    }

    /** Display text of a raw value; an empty value is "0", a trailing `.` keeps the separator. */
    fun format(rawDigits: String): String {
        val raw = parse(rawDigits)
        val integer = raw.substringBefore('.').ifEmpty { "0" }
        val grouped = integer.reversed().chunked(GROUP).joinToString(groupingSeparator.toString())
            .reversed()
        if (!raw.contains('.')) return grouped
        return grouped + decimalSeparator + raw.substringAfter('.')
    }

    /**
     * Raw value of typed or pasted [input]: with both `.` and `,` the last one starts the fraction,
     * otherwise the first mark of a kind that is not the grouping mark does; a separator typed
     * after one already exists is ignored, every other non-digit is dropped.
     */
    fun parse(input: String): String {
        val separatorAt = decimalIndex(input)
        val integerPart = input.take(if (separatorAt < 0) input.length else separatorAt).digits()
        val integer = integerPart.trimStart('0').ifEmpty { integerPart.take(1) }
        if (separatorAt < 0) return integer
        val fraction = input.drop(separatorAt + 1).digits().take(MAX_FRACTION)
        return integer.ifEmpty { "0" } + "." + fraction
    }

    private fun decimalIndex(input: String): Int {
        var marks = input.indices.filter { input[it] == '.' || input[it] == ',' }
        if (marks.size > 1 && marks.last() == input.lastIndex) marks = marks.dropLast(1)
        val kinds = marks.map { input[it] }.toSet()
        return when {
            marks.isEmpty() -> -1
            kinds.size > 1 -> marks.last()
            isDecimalMark(kinds.first()) -> marks.first()
            else -> -1
        }
    }

    /** '.' is the raw contract, so it stays a decimal even where it is also the grouping mark. */
    private fun isDecimalMark(char: Char) = char == '.' || char != groupingSeparator

    private fun String.digits() = filter { it in '0'..'9' }

    private companion object {
        const val THIN_SPACE = ' '
        const val GROUP = 3
        const val MAX_FRACTION = 2
    }
}
