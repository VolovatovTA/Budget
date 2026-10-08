package ru.bysoft.android.budget.uikit.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.uikit.icons.leftArrow

fun mainTopBar(
    text: String,
    onBackClick: ()-> Unit
): @Composable () -> Unit {
    return {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
        ) {
            Image(
                leftArrow,
                null,
                modifier = Modifier
                    .clickable { onBackClick.invoke() }
                    .padding(10.dp)
            )
            Text(
                text,
                modifier = Modifier.padding(horizontal = 10.dp).fillMaxWidth()
            )

        }
    }
}