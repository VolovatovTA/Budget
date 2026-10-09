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

val Plus: ImageVector
    get() {
        if (_Plus != null) {
            return _Plus!!
        }
        _Plus = Builder(
            name = "Plus", defaultWidth = 20.0.dp, defaultHeight =
            20.0.dp, viewportWidth = 20.0f, viewportHeight = 20.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(20.0f, 9.25f)
                horizontalLineTo(10.75f)
                verticalLineTo(0.0f)
                horizontalLineTo(9.25f)
                verticalLineTo(9.25f)
                horizontalLineTo(0.0f)
                verticalLineTo(10.75f)
                horizontalLineTo(9.25f)
                verticalLineTo(20.0f)
                horizontalLineTo(10.75f)
                verticalLineTo(10.75f)
                horizontalLineTo(20.0f)
                verticalLineTo(9.25f)
                close()
            }
        }
            .build()
        return _Plus!!
    }

private var _Plus: ImageVector? = null
