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

val Health: ImageVector
    get() {
        if (_Health != null) {
            return _Health!!
        }
        _Health = Builder(
            name = "Health", defaultWidth = 18.0.dp, defaultHeight =
            18.0.dp, viewportWidth = 18.0f, viewportHeight = 18.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(11.5f, 1.5f)
                verticalLineTo(6.5f)
                horizontalLineTo(16.5f)
                verticalLineTo(11.5f)
                horizontalLineTo(11.5f)
                verticalLineTo(16.5f)
                horizontalLineTo(6.5f)
                verticalLineTo(11.5f)
                horizontalLineTo(1.5f)
                verticalLineTo(6.5f)
                horizontalLineTo(6.5f)
                verticalLineTo(1.5f)
                horizontalLineTo(11.5f)
                close()
                moveTo(13.0f, 0.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(5.0f)
                horizontalLineTo(0.0f)
                verticalLineTo(13.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(18.0f)
                horizontalLineTo(13.0f)
                verticalLineTo(13.0f)
                horizontalLineTo(18.0f)
                verticalLineTo(5.0f)
                horizontalLineTo(13.0f)
                verticalLineTo(0.0f)
                close()
            }
        }
            .build()
        return _Health!!
    }

private var _Health: ImageVector? = null
