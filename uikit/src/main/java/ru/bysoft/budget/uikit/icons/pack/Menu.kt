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

val Menu: ImageVector
    get() {
        if (_Menu != null) {
            return _Menu!!
        }
        _Menu = Builder(
            name = "Menu", defaultWidth = 14.0.dp, defaultHeight =
            14.0.dp, viewportWidth = 14.0f, viewportHeight = 14.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(5.0f, 10.0f)
                horizontalLineTo(14.0f)
                verticalLineTo(8.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(10.0f)
                close()
                moveTo(5.0f, 14.0f)
                horizontalLineTo(14.0f)
                verticalLineTo(12.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(14.0f)
                close()
                moveTo(5.0f, 6.0f)
                horizontalLineTo(14.0f)
                verticalLineTo(4.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(6.0f)
                close()
                moveTo(5.0f, 0.0f)
                verticalLineTo(2.0f)
                horizontalLineTo(14.0f)
                verticalLineTo(0.0f)
                horizontalLineTo(5.0f)
                close()
                moveTo(0.0f, 14.0f)
                horizontalLineTo(2.0f)
                verticalLineTo(12.0f)
                horizontalLineTo(0.0f)
                verticalLineTo(14.0f)
                close()
                moveTo(0.0f, 10.0f)
                horizontalLineTo(2.0f)
                verticalLineTo(8.0f)
                horizontalLineTo(0.0f)
                verticalLineTo(10.0f)
                close()
                moveTo(0.0f, 2.0f)
                horizontalLineTo(2.0f)
                verticalLineTo(0.0f)
                horizontalLineTo(0.0f)
                verticalLineTo(2.0f)
                close()
                moveTo(0.0f, 6.0f)
                horizontalLineTo(2.0f)
                verticalLineTo(4.0f)
                horizontalLineTo(0.0f)
                verticalLineTo(6.0f)
                close()
            }
        }
            .build()
        return _Menu!!
    }

private var _Menu: ImageVector? = null
