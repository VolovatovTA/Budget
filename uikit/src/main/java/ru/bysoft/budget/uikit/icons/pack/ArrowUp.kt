package ru.bysoft.budget.uikit.icons.pack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ArrowUp: ImageVector
    get() {
        if (_ArrowUp != null) {
            return _ArrowUp!!
        }
        _ArrowUp = Builder(
            name = "ArrowUp", defaultWidth = 20.0.dp, defaultHeight =
            12.0.dp, viewportWidth = 20.0f, viewportHeight = 12.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(0.949f, 9.8181f)
                lineTo(10.0f, 0.7681f)
                lineTo(19.051f, 9.8181f)
                lineTo(17.637f, 11.2321f)
                lineTo(10.0f, 3.5961f)
                lineTo(2.363f, 11.2321f)
                lineTo(0.949f, 9.8181f)
                close()
            }
        }
            .build()
        return _ArrowUp!!
    }

private var _ArrowUp: ImageVector? = null
