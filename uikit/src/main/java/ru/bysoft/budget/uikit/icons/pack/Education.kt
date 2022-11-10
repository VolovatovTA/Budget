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

val Education: ImageVector
    get() {
        if (_Education != null) {
            return _Education!!
        }
        _Education = Builder(
            name = "Education", defaultWidth = 24.0.dp, defaultHeight =
            20.0.dp, viewportWidth = 24.0f, viewportHeight = 20.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0f, 0.04f)
                lineTo(0.0f, 7.0f)
                lineTo(5.0f, 9.9001f)
                verticalLineTo(13.3095f)
                curveTo(5.0f, 13.8361f, 5.1386f, 14.3534f, 5.4019f, 14.8095f)
                curveTo(5.6652f, 15.2656f, 6.0439f, 15.6443f, 6.5f, 15.9076f)
                lineTo(12.0f, 19.083f)
                lineTo(17.5f, 15.9076f)
                curveTo(17.9561f, 15.6443f, 18.3348f, 15.2656f, 18.5981f, 14.8095f)
                curveTo(18.8614f, 14.3534f, 19.0f, 13.8361f, 19.0f, 13.3095f)
                verticalLineTo(9.9001f)
                lineTo(22.5f, 7.8701f)
                verticalLineTo(14.0001f)
                horizontalLineTo(24.0f)
                verticalLineTo(7.0f)
                lineTo(12.0f, 0.04f)
                close()
                moveTo(12.0f, 1.774f)
                lineTo(19.7172f, 6.25f)
                horizontalLineTo(12.0f)
                verticalLineTo(7.7501f)
                horizontalLineTo(19.7172f)
                lineTo(12.0f, 12.2261f)
                lineTo(2.99f, 7.0f)
                lineTo(12.0f, 1.774f)
                close()
                moveTo(17.5f, 13.3095f)
                curveTo(17.4996f, 13.5727f, 17.4301f, 13.8312f, 17.2985f, 14.0592f)
                curveTo(17.1669f, 14.2871f, 16.9777f, 14.4766f, 16.75f, 14.6086f)
                lineTo(12.0f, 17.3509f)
                lineTo(7.25f, 14.6086f)
                curveTo(7.0223f, 14.4766f, 6.8331f, 14.2871f, 6.7015f, 14.0592f)
                curveTo(6.5699f, 13.8312f, 6.5004f, 13.5727f, 6.5f, 13.3095f)
                verticalLineTo(10.7701f)
                lineTo(12.0f, 13.9601f)
                lineTo(17.5f, 10.7701f)
                verticalLineTo(13.3095f)
                close()
                moveTo(21.0f, 7.006f)
                verticalLineTo(6.9941f)
                lineTo(21.01f, 7.0f)
                lineTo(21.0f, 7.006f)
                close()
            }
        }
            .build()
        return _Education!!
    }

private var _Education: ImageVector? = null
