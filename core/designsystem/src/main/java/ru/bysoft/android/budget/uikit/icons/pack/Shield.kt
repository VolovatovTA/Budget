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

val Shield: ImageVector
    get() {
        if (_Shield != null) {
            return _Shield!!
        }
        _Shield = Builder(
            name = "Shield", defaultWidth = 18.0.dp, defaultHeight =
            23.0.dp, viewportWidth = 18.0f, viewportHeight = 23.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(9.0f, 0.0f)
                lineTo(0.0f, 3.563f)
                verticalLineTo(11.1357f)
                curveTo(0.0029f, 13.066f, 0.5124f, 14.9618f, 1.4776f, 16.6335f)
                curveTo(2.4427f, 18.3052f, 3.8298f, 19.6943f, 5.5f, 20.662f)
                lineTo(9.0f, 22.6821f)
                lineTo(12.5f, 20.6616f)
                curveTo(14.1702f, 19.694f, 15.5572f, 18.3049f, 16.5223f, 16.6333f)
                curveTo(17.4875f, 14.9616f, 17.997f, 13.066f, 18.0f, 11.1357f)
                verticalLineTo(3.563f)
                lineTo(9.0f, 0.0f)
                close()
                moveTo(16.5f, 11.1357f)
                curveTo(16.4975f, 12.8028f, 16.0575f, 14.4401f, 15.2239f, 15.8838f)
                curveTo(14.3904f, 17.3276f, 13.1925f, 18.5273f, 11.75f, 19.363f)
                lineTo(9.0f, 20.9502f)
                lineTo(6.25f, 19.3625f)
                curveTo(4.8076f, 18.5268f, 3.6097f, 17.3272f, 2.7762f, 15.8836f)
                curveTo(1.9426f, 14.4399f, 1.5026f, 12.8027f, 1.5f, 11.1357f)
                verticalLineTo(4.5824f)
                lineTo(9.0f, 1.6133f)
                lineTo(16.5f, 4.5824f)
                verticalLineTo(11.1357f)
                close()
                moveTo(6.2656f, 9.1966f)
                lineTo(5.0645f, 10.3988f)
                lineTo(8.3975f, 13.7323f)
                lineTo(13.123f, 9.0072f)
                lineTo(11.9219f, 7.805f)
                lineTo(8.3975f, 11.328f)
                lineTo(6.2656f, 9.1966f)
                close()
            }
        }
            .build()
        return _Shield!!
    }

private var _Shield: ImageVector? = null
