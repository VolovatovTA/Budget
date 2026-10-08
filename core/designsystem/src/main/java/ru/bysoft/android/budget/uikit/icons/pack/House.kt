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

val House: ImageVector
    get() {
        if (_House != null) {
            return _House!!
        }
        _House = Builder(name = "House", defaultWidth = 18.0.dp, defaultHeight =
                19.0.dp, viewportWidth = 18.0f, viewportHeight = 19.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(9.0f, 0.8038f)
                lineTo(0.0f, 6.0f)
                verticalLineTo(19.0f)
                horizontalLineTo(18.0f)
                verticalLineTo(6.0f)
                lineTo(9.0f, 0.8038f)
                close()
                moveTo(7.5f, 17.5f)
                verticalLineTo(12.5f)
                horizontalLineTo(10.5f)
                verticalLineTo(17.5f)
                horizontalLineTo(7.5f)
                close()
                moveTo(16.5f, 17.5f)
                horizontalLineTo(12.0f)
                verticalLineTo(11.0f)
                horizontalLineTo(6.0f)
                verticalLineTo(17.5f)
                horizontalLineTo(1.5f)
                verticalLineTo(6.866f)
                lineTo(9.0f, 2.536f)
                lineTo(16.5f, 6.866f)
                verticalLineTo(17.5f)
                close()
            }
        }
        .build()
        return _House!!
    }

private var _House: ImageVector? = null
