package ru.bysoft.android.budget.uikit.components.amount

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle

/**
 * Shows a raw amount as grouped text with the unfilled minor digits appended: typed characters in
 * [typed], the grouping marks and the placeholder "0" / ",00" in [placeholder].
 */
internal class AmountVisualTransformation(
    private val formatter: AmountFormatter,
    private val typed: Color,
    private val placeholder: Color,
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val raw = text.text
        val shown = formatter.format(raw)
        val filled = formatter.parse(raw).substringAfter('.', "").length
        val padding = if (raw.contains('.')) {
            "0".repeat(MIN_FRACTION - filled)
        } else {
            "${formatter.decimalSeparator}00"
        }
        val styled = buildAnnotatedString {
            shown.forEach { char ->
                val isTyped = raw.isNotEmpty() && char != formatter.groupingSeparator
                withStyle(SpanStyle(color = if (isTyped) typed else placeholder)) { append(char) }
            }
            withStyle(SpanStyle(color = placeholder)) { append(padding) }
        }
        return TransformedText(styled, AmountOffsetMapping(shown, raw.length, formatter))
    }

    private companion object {
        const val MIN_FRACTION = 2
    }
}

/** Keeps the cursor on the same typed character while grouping marks come and go. */
internal class AmountOffsetMapping(
    shown: String,
    private val rawLength: Int,
    formatter: AmountFormatter,
) : OffsetMapping {

    private val shownAt: IntArray = run {
        val ends = shown.indices.filter { shown[it] != formatter.groupingSeparator }.map { it + 1 }
        IntArray(ends.size + 1) { if (it == 0) 0 else ends[it - 1] }
    }

    override fun originalToTransformed(offset: Int): Int =
        shownAt[offset.coerceIn(0, shownAt.lastIndex)]

    override fun transformedToOriginal(offset: Int): Int =
        shownAt.count { it in 1..offset }.coerceAtMost(rawLength)
}
