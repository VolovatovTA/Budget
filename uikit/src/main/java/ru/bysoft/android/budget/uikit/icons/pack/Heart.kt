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

val Heart: ImageVector
    get() {
        if (_Heart != null) {
            return _Heart!!
        }
        _Heart = Builder(
            name = "Heart", defaultWidth = 20.0.dp, defaultHeight =
            19.0.dp, viewportWidth = 20.0f, viewportHeight = 19.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(14.6685f, 1.5f)
                curveTo(17.4966f, 1.5f, 18.5f, 3.8194f, 18.5f, 5.8055f)
                curveTo(18.5f, 9.2595f, 13.8058f, 14.359f, 10.0f, 17.1655f)
                curveTo(6.1943f, 14.359f, 1.5f, 9.26f, 1.5f, 5.8055f)
                curveTo(1.5f, 3.8194f, 2.5035f, 1.5f, 5.3316f, 1.5f)
                curveTo(6.0349f, 1.5475f, 6.7167f, 1.762f, 7.3204f, 2.1257f)
                curveTo(7.9242f, 2.4895f, 8.4325f, 2.992f, 8.8031f, 3.5916f)
                lineTo(10.0f, 5.1761f)
                lineTo(11.1969f, 3.5916f)
                curveTo(11.5675f, 2.992f, 12.0758f, 2.4895f, 12.6796f, 2.1257f)
                curveTo(13.2834f, 1.762f, 13.9652f, 1.5474f, 14.6685f, 1.5f)
                close()
                moveTo(14.6685f, 0.0f)
                curveTo(13.7327f, 0.0446f, 12.8211f, 0.3116f, 12.0092f, 0.779f)
                curveTo(11.1972f, 1.2464f, 10.5085f, 1.9007f, 10.0f, 2.6875f)
                curveTo(9.4915f, 1.9007f, 8.8028f, 1.2464f, 7.9909f, 0.779f)
                curveTo(7.179f, 0.3117f, 6.2674f, 0.0446f, 5.3316f, 0.0f)
                curveTo(1.9564f, 0.0f, 0.0f, 2.5992f, 0.0f, 5.8055f)
                curveTo(0.0f, 10.5555f, 6.4583f, 16.6251f, 10.0f, 19.0f)
                curveTo(13.5417f, 16.6251f, 20.0f, 10.5555f, 20.0f, 5.8055f)
                curveTo(20.0f, 2.5992f, 18.0436f, 0.0f, 14.6685f, 0.0f)
                close()
            }
        }
            .build()
        return _Heart!!
    }

private var _Heart: ImageVector? = null
