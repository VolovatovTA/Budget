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

val Multiply: ImageVector
    get() {
        if (_Multiply != null) {
            return _Multiply!!
        }
        _Multiply = Builder(
            name = "Multiply", defaultWidth = 18.0.dp, defaultHeight =
            18.0.dp, viewportWidth = 18.0f, viewportHeight = 18.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(17.53f, 1.53f)
                lineTo(16.47f, 0.47f)
                lineTo(9.0f, 7.939f)
                lineTo(1.53f, 0.47f)
                lineTo(0.47f, 1.53f)
                lineTo(7.939f, 9.0f)
                lineTo(0.47f, 16.47f)
                lineTo(1.53f, 17.53f)
                lineTo(9.0f, 10.061f)
                lineTo(16.47f, 17.53f)
                lineTo(17.53f, 16.47f)
                lineTo(10.061f, 9.0f)
                lineTo(17.53f, 1.53f)
                close()
            }
        }
            .build()
        return _Multiply!!
    }

private var _Multiply: ImageVector? = null
