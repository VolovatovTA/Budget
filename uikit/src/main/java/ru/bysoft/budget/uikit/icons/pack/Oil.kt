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

val Oil: ImageVector
    get() {
        if (_Oil != null) {
            return _Oil!!
        }
        _Oil = Builder(
            name = "Oil", defaultWidth = 20.0.dp, defaultHeight =
            18.0.dp, viewportWidth = 20.0f, viewportHeight = 18.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(16.3428f, 0.0322f)
                lineTo(15.2822f, 1.0928f)
                lineTo(18.3613f, 4.1719f)
                horizontalLineTo(17.5f)
                curveTo(16.837f, 4.1719f, 16.2011f, 4.4353f, 15.7322f, 4.9041f)
                curveTo(15.2634f, 5.373f, 15.0f, 6.0089f, 15.0f, 6.6719f)
                curveTo(15.0f, 7.3349f, 15.2634f, 7.9708f, 15.7322f, 8.4397f)
                curveTo(16.2011f, 8.9085f, 16.837f, 9.1719f, 17.5f, 9.1719f)
                horizontalLineTo(18.5f)
                verticalLineTo(15.25f)
                curveTo(18.5f, 15.5815f, 18.3683f, 15.8995f, 18.1339f, 16.1339f)
                curveTo(17.8995f, 16.3683f, 17.5815f, 16.5f, 17.25f, 16.5f)
                curveTo(16.9185f, 16.5f, 16.6005f, 16.3683f, 16.3661f, 16.1339f)
                curveTo(16.1317f, 15.8995f, 16.0f, 15.5815f, 16.0f, 15.25f)
                verticalLineTo(13.0f)
                curveTo(15.9993f, 12.2709f, 15.7093f, 11.5718f, 15.1937f, 11.0563f)
                curveTo(14.6782f, 10.5407f, 13.9791f, 10.2507f, 13.25f, 10.25f)
                horizontalLineTo(12.0f)
                verticalLineTo(3.0f)
                curveTo(12.0f, 2.2043f, 11.6839f, 1.4413f, 11.1213f, 0.8787f)
                curveTo(10.5587f, 0.3161f, 9.7956f, 0.0f, 9.0f, 0.0f)
                horizontalLineTo(3.0f)
                curveTo(2.2043f, 0.0f, 1.4413f, 0.3161f, 0.8787f, 0.8787f)
                curveTo(0.3161f, 1.4413f, 0.0f, 2.2043f, 0.0f, 3.0f)
                verticalLineTo(18.0f)
                horizontalLineTo(12.0f)
                verticalLineTo(11.75f)
                horizontalLineTo(13.25f)
                curveTo(13.5814f, 11.7503f, 13.8992f, 11.8821f, 14.1336f, 12.1164f)
                curveTo(14.3679f, 12.3508f, 14.4997f, 12.6686f, 14.5f, 13.0f)
                verticalLineTo(15.25f)
                curveTo(14.5f, 15.9793f, 14.7897f, 16.6788f, 15.3055f, 17.1945f)
                curveTo(15.8212f, 17.7103f, 16.5207f, 18.0f, 17.25f, 18.0f)
                curveTo(17.9793f, 18.0f, 18.6788f, 17.7103f, 19.1945f, 17.1945f)
                curveTo(19.7103f, 16.6788f, 20.0f, 15.9793f, 20.0f, 15.25f)
                verticalLineTo(3.69f)
                lineTo(16.3428f, 0.0322f)
                close()
                moveTo(3.0f, 1.5f)
                horizontalLineTo(9.0f)
                curveTo(9.3977f, 1.5005f, 9.779f, 1.6586f, 10.0602f, 1.9398f)
                curveTo(10.3414f, 2.221f, 10.4995f, 2.6023f, 10.5f, 3.0f)
                verticalLineTo(7.0f)
                horizontalLineTo(1.5f)
                verticalLineTo(3.0f)
                curveTo(1.5005f, 2.6023f, 1.6586f, 2.221f, 1.9398f, 1.9398f)
                curveTo(2.221f, 1.6586f, 2.6023f, 1.5005f, 3.0f, 1.5f)
                close()
                moveTo(1.5f, 16.5f)
                verticalLineTo(8.5f)
                horizontalLineTo(10.5f)
                verticalLineTo(16.5f)
                horizontalLineTo(1.5f)
                close()
                moveTo(17.5f, 7.6719f)
                curveTo(17.2348f, 7.6719f, 16.9804f, 7.5665f, 16.7929f, 7.379f)
                curveTo(16.6054f, 7.1915f, 16.5f, 6.9371f, 16.5f, 6.6719f)
                curveTo(16.5f, 6.4067f, 16.6054f, 6.1523f, 16.7929f, 5.9648f)
                curveTo(16.9804f, 5.7773f, 17.2348f, 5.6719f, 17.5f, 5.6719f)
                horizontalLineTo(18.5f)
                verticalLineTo(7.6719f)
                horizontalLineTo(17.5f)
                close()
            }
        }
            .build()
        return _Oil!!
    }

private var _Oil: ImageVector? = null
