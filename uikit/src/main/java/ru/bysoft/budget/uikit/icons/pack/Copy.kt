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

val Copy: ImageVector
    get() {
        if (_Copy != null) {
            return _Copy!!
        }
        _Copy = Builder(
            name = "Copy", defaultWidth = 16.0.dp, defaultHeight =
            18.0.dp, viewportWidth = 16.0f, viewportHeight = 18.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(1.5f, 4.0f)
                horizontalLineTo(0.0f)
                verticalLineTo(18.0f)
                horizontalLineTo(12.0f)
                verticalLineTo(16.5f)
                horizontalLineTo(1.5f)
                verticalLineTo(4.0f)
                close()
                moveTo(4.0f, 0.0f)
                verticalLineTo(14.0f)
                horizontalLineTo(16.0f)
                verticalLineTo(0.0f)
                horizontalLineTo(4.0f)
                close()
                moveTo(14.5f, 12.5f)
                horizontalLineTo(5.5f)
                verticalLineTo(1.5f)
                horizontalLineTo(14.5f)
                verticalLineTo(12.5f)
                close()
            }
        }
            .build()
        return _Copy!!
    }

private var _Copy: ImageVector? = null
