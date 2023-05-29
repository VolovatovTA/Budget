package ru.bysoft.android.budget.uikit.components.buttons.preview

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonSize
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.padding

@Preview(widthDp = 1600)
@Composable
fun Light() {
    Row {
        ButtonType.values().forEach {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = it.name,
                    style = UiKitTypography.TextMD.Medium,
                    color = Color.White,
                    modifier = Modifier.padding(padding)
                )
                Row {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(padding),
                        modifier = Modifier.padding(padding)
                    ) {
                        ButtonSize.values().forEach { size ->
                            UiKitButton(
                                info = UiKitButtonInfo(
                                    text = size.name,
                                    size = size,
                                    type = it
                                ),
                                modifier = Modifier.width(100.dp),
                                isButtonEnabled = false
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(padding),
                        modifier = Modifier.padding(padding)
                    ) {
                        ButtonSize.values().forEach { size ->
                            UiKitButton(
                                info = UiKitButtonInfo(
                                    text = size.name,
                                    size = size,
                                    type = it
                                ),
                                modifier = Modifier.width(100.dp),
                                isButtonEnabled = true
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 1600)
@Composable
fun Dark() {
    Row {
        ButtonType.values().forEach {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = it.name,
                    style = UiKitTypography.TextMD.Medium,
                    color = Color.White,
                    modifier = Modifier.padding(padding)
                )
                Row {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(padding),
                        modifier = Modifier.padding(padding)
                    ) {
                        ButtonSize.values().forEach { size ->
                            UiKitButton(
                                info = UiKitButtonInfo(
                                    text = size.name,
                                    size = size,
                                    type = it
                                ),
                                modifier = Modifier.width(100.dp),
                                isButtonEnabled = false
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(padding),
                        modifier = Modifier.padding(padding)
                    ) {
                        ButtonSize.values().forEach { size ->
                            UiKitButton(
                                info = UiKitButtonInfo(
                                    text = size.name,
                                    size = size,
                                    type = it
                                ),
                                modifier = Modifier.width(100.dp),
                                isButtonEnabled = true
                            )
                        }
                    }
                }
            }
        }
    }
}