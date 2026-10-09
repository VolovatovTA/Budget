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

val Tele: ImageVector
    get() {
        if (_Tele != null) {
            return _Tele!!
        }
        _Tele = Builder(
            name = "Tele", defaultWidth = 20.0.dp, defaultHeight =
            21.0.dp, viewportWidth = 20.0f, viewportHeight = 21.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(14.2427f, 5.0f)
                lineTo(10.0f, 0.7573f)
                lineTo(5.7573f, 5.0f)
                horizontalLineTo(0.0f)
                verticalLineTo(21.0f)
                horizontalLineTo(20.0f)
                verticalLineTo(5.0f)
                horizontalLineTo(14.2427f)
                close()
                moveTo(10.0f, 3.5858f)
                lineTo(11.4142f, 5.0f)
                horizontalLineTo(8.5858f)
                lineTo(10.0f, 3.5858f)
                close()
                moveTo(18.0f, 19.0f)
                horizontalLineTo(2.0f)
                verticalLineTo(7.0f)
                horizontalLineTo(18.0f)
                verticalLineTo(19.0f)
                close()
                moveTo(16.0f, 9.0f)
                horizontalLineTo(4.0f)
                verticalLineTo(17.0f)
                horizontalLineTo(16.0f)
                verticalLineTo(9.0f)
                close()
                moveTo(14.0f, 15.0f)
                horizontalLineTo(6.0f)
                verticalLineTo(11.0f)
                horizontalLineTo(14.0f)
                verticalLineTo(15.0f)
                close()
            }
        }
            .build()
        return _Tele!!
    }

private var _Tele: ImageVector? = null
