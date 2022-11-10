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

val Clear: ImageVector
    get() {
        if (_Clear != null) {
            return _Clear!!
        }
        _Clear = Builder(
            name = "Clear", defaultWidth = 20.0.dp, defaultHeight =
            20.0.dp, viewportWidth = 20.0f, viewportHeight = 20.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(10.0f, 0.0f)
                curveTo(8.0222f, 0.0f, 6.0888f, 0.5865f, 4.4443f, 1.6853f)
                curveTo(2.7998f, 2.7841f, 1.5181f, 4.3459f, 0.7612f, 6.1732f)
                curveTo(0.0043f, 8.0004f, -0.1937f, 10.0111f, 0.1922f, 11.9509f)
                curveTo(0.578f, 13.8907f, 1.5304f, 15.6725f, 2.9289f, 17.0711f)
                curveTo(4.3275f, 18.4696f, 6.1093f, 19.422f, 8.0491f, 19.8079f)
                curveTo(9.9889f, 20.1937f, 11.9996f, 19.9957f, 13.8268f, 19.2388f)
                curveTo(15.6541f, 18.4819f, 17.2159f, 17.2002f, 18.3147f, 15.5557f)
                curveTo(19.4135f, 13.9112f, 20.0f, 11.9778f, 20.0f, 10.0f)
                curveTo(20.0f, 8.6868f, 19.7413f, 7.3864f, 19.2388f, 6.1732f)
                curveTo(18.7363f, 4.9599f, 17.9997f, 3.8575f, 17.0711f, 2.9289f)
                curveTo(16.1425f, 2.0003f, 15.0401f, 1.2637f, 13.8268f, 0.7612f)
                curveTo(12.6136f, 0.2587f, 11.3132f, 0.0f, 10.0f, 0.0f)
                close()
                moveTo(10.0f, 18.0f)
                curveTo(8.4178f, 18.0f, 6.871f, 17.5308f, 5.5554f, 16.6518f)
                curveTo(4.2399f, 15.7727f, 3.2145f, 14.5233f, 2.609f, 13.0615f)
                curveTo(2.0035f, 11.5997f, 1.845f, 9.9911f, 2.1537f, 8.4393f)
                curveTo(2.4624f, 6.8874f, 3.2243f, 5.462f, 4.3432f, 4.3432f)
                curveTo(5.462f, 3.2243f, 6.8874f, 2.4624f, 8.4393f, 2.1537f)
                curveTo(9.9911f, 1.845f, 11.5997f, 2.0035f, 13.0615f, 2.609f)
                curveTo(14.5233f, 3.2145f, 15.7727f, 4.2398f, 16.6518f, 5.5554f)
                curveTo(17.5308f, 6.871f, 18.0f, 8.4177f, 18.0f, 10.0f)
                curveTo(17.9976f, 12.121f, 17.154f, 14.1544f, 15.6542f, 15.6542f)
                curveTo(14.1544f, 17.154f, 12.121f, 17.9976f, 10.0f, 18.0f)
                close()
                moveTo(11.8716f, 6.9966f)
                lineTo(10.0f, 8.8682f)
                lineTo(8.1284f, 6.9966f)
                lineTo(6.9966f, 8.1284f)
                lineTo(8.8682f, 10.0f)
                lineTo(6.9966f, 11.8716f)
                lineTo(8.1284f, 13.0034f)
                lineTo(10.0f, 11.1318f)
                lineTo(11.8716f, 13.0034f)
                lineTo(13.0034f, 11.8716f)
                lineTo(11.1318f, 10.0f)
                lineTo(13.0034f, 8.1284f)
                lineTo(11.8716f, 6.9966f)
                close()
            }
        }
            .build()
        return _Clear!!
    }

private var _Clear: ImageVector? = null
