package ru.bysoft.android.budget.uikit.components.amount

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.styles.BudgetShapes
import ru.bysoft.android.budget.uikit.styles.BudgetSpacing
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.theme.BudgetTheme

/**
 * The big centred amount input. [value] is the raw amount (digits with an optional `.` fraction);
 * the field shows it grouped for the device locale and reports the raw value back through
 * [onValueChange]; [label] names the input for screen readers. [currency] sits next to the amount, the pill under it shows [chipLabel]
 * (currency / wallet) and calls [onChipClick], where the caller opens its picker.
 */
@Composable
fun UiKitAmountField(
    value: String,
    onValueChange: (String) -> Unit,
    currency: String,
    chipLabel: String,
    label: String,
    onChipClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tokens = UiKitColors.colors.tokens
    val locale = LocalConfiguration.current.locales[0]
    val formatter = remember(locale) { AmountFormatter(locale) }
    val transformation = remember(formatter, tokens) {
        AmountVisualTransformation(formatter, tokens.textHigh, tokens.textDisabled)
    }
    val style = UiKitTypography.DisplayMD.Bold.copy(
        fontSize = AMOUNT_SIZE,
        lineHeight = AMOUNT_LINE_HEIGHT,
        fontWeight = FontWeight.ExtraBold,
        color = tokens.textHigh,
        textAlign = TextAlign.Center,
    )
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BudgetSpacing.s),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                value = value,
                onValueChange = { onValueChange(formatter.parse(it)) },
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .semantics { contentDescription = label },
                textStyle = style,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                visualTransformation = transformation,
                cursorBrush = SolidColor(tokens.accent),
            )
            Text(
                text = currency,
                style = UiKitTypography.DisplayXS.SemiBold,
                color = tokens.textMedium,
                modifier = Modifier.padding(start = BudgetSpacing.s),
            )
        }
        Box(
            modifier = Modifier
                .heightIn(min = MIN_TOUCH)
                .clip(BudgetShapes.pill)
                .background(tokens.surface2)
                .clickable(role = Role.Button, onClick = onChipClick)
                .padding(horizontal = BudgetSpacing.l),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = chipLabel,
                style = UiKitTypography.TextSM.SemiBold,
                color = tokens.textHigh,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

private val AMOUNT_SIZE = 44.sp
private val AMOUNT_LINE_HEIGHT = 52.sp
private val MIN_TOUCH = 48.dp

@Preview(name = "light")
@Preview(name = "dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AmountFieldPreview() {
    BudgetTheme {
        Column(modifier = Modifier.padding(BudgetSpacing.l)) {
            UiKitAmountField(
                value = "1234.5",
                onValueChange = {},
                currency = "€",
                chipLabel = "Card · EUR",
                label = "Amount",
                onChipClick = {},
            )
            UiKitAmountField(
                value = "",
                onValueChange = {},
                currency = "$",
                chipLabel = "Cash · USD",
                label = "Amount",
                onChipClick = {},
            )
        }
    }
}
