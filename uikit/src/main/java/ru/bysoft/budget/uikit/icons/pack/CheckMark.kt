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

val CheckMark: ImageVector
    get() {
        if (_CheckMark != null) {
            return _CheckMark!!
        }
        _CheckMark = Builder(
            name = "CheckMark", defaultWidth = 22.0.dp, defaultHeight =
            16.0.dp, viewportWidth = 22.0f, viewportHeight = 16.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(8.5f, 15.045f)
                lineTo(0.47f, 7.015f)
                lineTo(1.53f, 5.955f)
                lineTo(8.5f, 12.924f)
                lineTo(20.47f, 0.955f)
                lineTo(21.53f, 2.015f)
                lineTo(8.5f, 15.045f)
                close()
            }
        }
            .build()
        return _CheckMark!!
    }

private var _CheckMark: ImageVector? = null
