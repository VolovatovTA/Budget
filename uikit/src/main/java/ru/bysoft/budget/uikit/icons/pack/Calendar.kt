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

public val Calendar: ImageVector
    get() {
        if (_Calendar != null) {
            return _Calendar!!
        }
        _Calendar = Builder(name = "Calendar", defaultWidth = 20.0.dp, defaultHeight =
                19.0.dp, viewportWidth = 20.0f, viewportHeight = 19.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(7.0f, 8.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(10.0f)
                horizontalLineTo(7.0f)
                verticalLineTo(8.0f)
                close()
                moveTo(11.0f, 8.0f)
                horizontalLineTo(9.0f)
                verticalLineTo(10.0f)
                horizontalLineTo(11.0f)
                verticalLineTo(8.0f)
                close()
                moveTo(15.0f, 8.0f)
                horizontalLineTo(13.0f)
                verticalLineTo(10.0f)
                horizontalLineTo(15.0f)
                verticalLineTo(8.0f)
                close()
                moveTo(7.0f, 12.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(14.0f)
                horizontalLineTo(7.0f)
                verticalLineTo(12.0f)
                close()
                moveTo(11.0f, 12.0f)
                horizontalLineTo(9.0f)
                verticalLineTo(14.0f)
                horizontalLineTo(11.0f)
                verticalLineTo(12.0f)
                close()
                moveTo(15.0f, 12.0f)
                horizontalLineTo(13.0f)
                verticalLineTo(14.0f)
                horizontalLineTo(15.0f)
                verticalLineTo(12.0f)
                close()
                moveTo(16.0f, 3.0f)
                verticalLineTo(1.0f)
                curveTo(16.0f, 0.7348f, 15.8946f, 0.4804f, 15.7071f, 0.2929f)
                curveTo(15.5196f, 0.1054f, 15.2652f, 0.0f, 15.0f, 0.0f)
                curveTo(14.7348f, 0.0f, 14.4804f, 0.1054f, 14.2929f, 0.2929f)
                curveTo(14.1054f, 0.4804f, 14.0f, 0.7348f, 14.0f, 1.0f)
                verticalLineTo(3.0f)
                horizontalLineTo(6.0f)
                verticalLineTo(1.0f)
                curveTo(6.0f, 0.7348f, 5.8946f, 0.4804f, 5.7071f, 0.2929f)
                curveTo(5.5196f, 0.1054f, 5.2652f, 0.0f, 5.0f, 0.0f)
                curveTo(4.7348f, 0.0f, 4.4804f, 0.1054f, 4.2929f, 0.2929f)
                curveTo(4.1054f, 0.4804f, 4.0f, 0.7348f, 4.0f, 1.0f)
                verticalLineTo(3.0f)
                horizontalLineTo(0.0f)
                verticalLineTo(19.0f)
                horizontalLineTo(20.0f)
                verticalLineTo(3.0f)
                horizontalLineTo(16.0f)
                close()
                moveTo(18.0f, 17.0f)
                horizontalLineTo(2.0f)
                verticalLineTo(5.0f)
                horizontalLineTo(18.0f)
                verticalLineTo(17.0f)
                close()
            }
        }
        .build()
        return _Calendar!!
    }

private var _Calendar: ImageVector? = null
