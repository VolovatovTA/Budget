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

val Statistic: ImageVector
    get() {
        if (_Statistic != null) {
            return _Statistic!!
        }
        _Statistic = Builder(
            name = "Statistic", defaultWidth = 22.0.dp, defaultHeight =
            12.0.dp, viewportWidth = 22.0f, viewportHeight = 12.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(2.03f, 11.4609f)
                lineTo(0.97f, 10.3999f)
                lineTo(8.467f, 2.9039f)
                lineTo(13.036f, 7.4729f)
                lineTo(19.97f, 0.5389f)
                lineTo(21.03f, 1.5989f)
                lineTo(13.036f, 9.5939f)
                lineTo(8.467f, 5.0249f)
                lineTo(2.03f, 11.4609f)
                close()
            }
        }
            .build()
        return _Statistic!!
    }

private var _Statistic: ImageVector? = null
