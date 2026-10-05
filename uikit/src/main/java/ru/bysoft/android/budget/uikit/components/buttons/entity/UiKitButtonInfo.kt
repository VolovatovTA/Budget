package ru.bysoft.android.budget.uikit.components.buttons.entity

import androidx.compose.ui.graphics.painter.Painter

data class UiKitButtonInfo(
    val text: String,
    val size: ButtonSize = ButtonSize.MEDIUM,
    val type: ButtonType = ButtonType.PRIMARY,
    val painterLeftImage: Painter? = null,
)
