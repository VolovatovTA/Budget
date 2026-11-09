package ru.bysoft.android.budget.uikit.components.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter

/**
 * An image that carries no meaning of its own: the text next to it already says everything
 * (a logo inside a labelled button, a chevron after a row title). TalkBack skips it instead of
 * reading the label twice. Every other image needs a real `contentDescription`; writing
 * `contentDescription = null` outside this file is forbidden (detekt, AGENTS.md).
 */
@Composable
fun UiKitDecorativeImage(painter: Painter, modifier: Modifier = Modifier) {
    Image(painter = painter, contentDescription = null, modifier = modifier)
}
