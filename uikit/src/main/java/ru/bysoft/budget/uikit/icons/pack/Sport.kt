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

val Sport: ImageVector
    get() {
        if (_Sport != null) {
            return _Sport!!
        }
        _Sport = Builder(
            name = "Sport", defaultWidth = 20.0.dp, defaultHeight =
            20.0.dp, viewportWidth = 20.0f, viewportHeight = 20.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(9.6465f, 1.8683f)
                lineTo(13.3586f, 5.58f)
                lineTo(5.5808f, 13.359f)
                lineTo(1.8683f, 9.6464f)
                lineTo(0.8076f, 10.7071f)
                lineTo(9.293f, 19.1924f)
                lineTo(10.3535f, 18.1317f)
                lineTo(6.6414f, 14.42f)
                lineTo(14.4192f, 6.641f)
                lineTo(18.1317f, 10.3536f)
                lineTo(19.1924f, 9.2929f)
                lineTo(10.707f, 0.8076f)
                lineTo(9.6465f, 1.8683f)
                close()
                moveTo(19.8994f, 5.7574f)
                lineTo(14.2427f, 0.1005f)
                lineTo(13.182f, 1.1612f)
                lineTo(18.8389f, 6.818f)
                lineTo(19.8994f, 5.7574f)
                close()
                moveTo(0.1006f, 14.2426f)
                lineTo(5.7573f, 19.9f)
                lineTo(6.818f, 18.8388f)
                lineTo(1.1611f, 13.182f)
                lineTo(0.1006f, 14.2426f)
                close()
            }
        }
            .build()
        return _Sport!!
    }

private var _Sport: ImageVector? = null
