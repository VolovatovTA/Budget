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

val Recycle: ImageVector
    get() {
        if (_Recycle != null) {
            return _Recycle!!
        }
        _Recycle = Builder(
            name = "Recycle", defaultWidth = 20.0.dp, defaultHeight =
            22.0.dp, viewportWidth = 20.0f, viewportHeight = 22.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(17.9487f, 6.0512f)
                lineTo(16.8818f, 7.1182f)
                curveTo(17.6169f, 8.0361f, 18.0774f, 9.1431f, 18.2102f, 10.3116f)
                curveTo(18.3429f, 11.4801f, 18.1425f, 12.6622f, 17.632f, 13.7216f)
                curveTo(17.1216f, 14.781f, 16.3219f, 15.6745f, 15.3253f, 16.2988f)
                curveTo(14.3288f, 16.9231f, 13.176f, 17.2529f, 12.0f, 17.25f)
                horizontalLineTo(7.2159f)
                verticalLineTo(14.7841f)
                lineTo(4.0f, 18.0f)
                lineTo(7.2159f, 21.2159f)
                verticalLineTo(18.75f)
                horizontalLineTo(12.0f)
                curveTo(13.4727f, 18.7526f, 14.9155f, 18.3348f, 16.1589f, 17.5457f)
                curveTo(17.4024f, 16.7567f, 18.3947f, 15.6291f, 19.0195f, 14.2955f)
                curveTo(19.6442f, 12.9619f, 19.8753f, 11.4777f, 19.6856f, 10.0173f)
                curveTo(19.4959f, 8.5569f, 18.8934f, 7.1809f, 17.9487f, 6.0512f)
                close()
                moveTo(8.0f, 4.75f)
                horizontalLineTo(12.7841f)
                verticalLineTo(7.2159f)
                lineTo(16.0f, 4.0f)
                lineTo(12.7841f, 0.7841f)
                verticalLineTo(3.25f)
                horizontalLineTo(8.0f)
                curveTo(6.5273f, 3.2475f, 5.0844f, 3.6653f, 3.8409f, 4.4545f)
                curveTo(2.5974f, 5.2436f, 1.605f, 6.3713f, 0.9801f, 7.7049f)
                curveTo(0.3553f, 9.0386f, 0.1241f, 10.5228f, 0.3136f, 11.9833f)
                curveTo(0.5031f, 13.4439f, 1.1055f, 14.8199f, 2.05f, 15.95f)
                lineTo(3.12f, 14.88f)
                curveTo(2.3844f, 13.9625f, 1.9235f, 12.8556f, 1.7904f, 11.6872f)
                curveTo(1.6573f, 10.5188f, 1.8575f, 9.3366f, 2.3679f, 8.2772f)
                curveTo(2.8782f, 7.2178f, 3.6779f, 6.3243f, 4.6745f, 5.7002f)
                curveTo(5.6712f, 5.076f, 6.8241f, 4.7466f, 8.0f, 4.75f)
                close()
            }
        }
            .build()
        return _Recycle!!
    }

private var _Recycle: ImageVector? = null
