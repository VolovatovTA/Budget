package ru.bysoft.android.budget.uikit.icons.pack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ArrowRight: ImageVector
    get() {
        if (_ArrowRight != null) {
            return _ArrowRight!!
        }
        _ArrowRight = Builder(
            name = "ArrowRight", defaultWidth = 12.0.dp, defaultHeight =
            20.0.dp, viewportWidth = 12.0f, viewportHeight = 20.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(2.182f, 0.949f)
                lineTo(11.232f, 10.0f)
                lineTo(2.182f, 19.051f)
                lineTo(0.768f, 17.637f)
                lineTo(8.404f, 10.0f)
                lineTo(0.768f, 2.363f)
                lineTo(2.182f, 0.949f)
                close()
            }
        }
            .build()
        return _ArrowRight!!
    }

private var _ArrowRight: ImageVector? = null
