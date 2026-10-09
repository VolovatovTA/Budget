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

val Edit: ImageVector
    get() {
        if (_Edit != null) {
            return _Edit!!
        }
        _Edit = Builder(
            name = "Edit", defaultWidth = 19.0.dp, defaultHeight =
            19.0.dp, viewportWidth = 19.0f, viewportHeight = 19.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(17.5859f, 2.5859f)
                lineTo(16.4141f, 1.4141f)
                curveTo(15.8512f, 0.8522f, 15.0884f, 0.5366f, 14.293f, 0.5366f)
                curveTo(13.4976f, 0.5366f, 12.7348f, 0.8522f, 12.1719f, 1.4141f)
                lineTo(0.0f, 13.5859f)
                verticalLineTo(19.0f)
                horizontalLineTo(5.4141f)
                lineTo(17.5859f, 6.8281f)
                curveTo(18.1478f, 6.2652f, 18.4634f, 5.5024f, 18.4634f, 4.707f)
                curveTo(18.4634f, 3.9117f, 18.1478f, 3.1488f, 17.5859f, 2.5859f)
                close()
                moveTo(4.7927f, 17.5f)
                horizontalLineTo(1.5f)
                verticalLineTo(14.2073f)
                lineTo(10.8234f, 4.8839f)
                lineTo(14.1161f, 8.1766f)
                lineTo(4.7927f, 17.5f)
                close()
                moveTo(16.5253f, 5.7675f)
                lineTo(15.1766f, 7.1161f)
                lineTo(11.8839f, 3.8234f)
                lineTo(13.2325f, 2.4747f)
                curveTo(13.5141f, 2.1942f, 13.8954f, 2.0366f, 14.2929f, 2.0366f)
                curveTo(14.6905f, 2.0366f, 15.0718f, 2.1942f, 15.3534f, 2.4747f)
                lineTo(16.5255f, 3.6468f)
                curveTo(16.806f, 3.9284f, 16.9635f, 4.3097f, 16.9635f, 4.7072f)
                curveTo(16.9635f, 5.1047f, 16.8058f, 5.4859f, 16.5253f, 5.7675f)
                close()
            }
        }
            .build()
        return _Edit!!
    }

private var _Edit: ImageVector? = null
