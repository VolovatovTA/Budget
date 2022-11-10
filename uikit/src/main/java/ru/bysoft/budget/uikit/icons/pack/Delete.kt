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

val Delete: ImageVector
    get() {
        if (_Delete != null) {
            return _Delete!!
        }
        _Delete = Builder(
            name = "Delete", defaultWidth = 18.0.dp, defaultHeight =
            21.0.dp, viewportWidth = 18.0f, viewportHeight = 21.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(18.0f, 4.0f)
                horizontalLineTo(13.0f)
                verticalLineTo(2.0f)
                curveTo(13.0f, 1.4696f, 12.7893f, 0.9609f, 12.4142f, 0.5858f)
                curveTo(12.0391f, 0.2107f, 11.5304f, 0.0f, 11.0f, 0.0f)
                horizontalLineTo(7.0f)
                curveTo(6.4696f, 0.0f, 5.9609f, 0.2107f, 5.5858f, 0.5858f)
                curveTo(5.2107f, 0.9609f, 5.0f, 1.4696f, 5.0f, 2.0f)
                verticalLineTo(4.0f)
                horizontalLineTo(0.0f)
                verticalLineTo(6.0f)
                horizontalLineTo(1.91f)
                lineTo(3.265f, 21.0f)
                horizontalLineTo(14.735f)
                lineTo(16.09f, 6.0f)
                horizontalLineTo(18.0f)
                verticalLineTo(4.0f)
                close()
                moveTo(7.0f, 2.0f)
                horizontalLineTo(11.0f)
                verticalLineTo(4.0f)
                horizontalLineTo(7.0f)
                verticalLineTo(2.0f)
                close()
                moveTo(12.9077f, 19.0f)
                horizontalLineTo(5.0923f)
                lineTo(3.918f, 6.0f)
                horizontalLineTo(14.082f)
                lineTo(12.9077f, 19.0f)
                close()
            }
        }
            .build()
        return _Delete!!
    }

private var _Delete: ImageVector? = null
