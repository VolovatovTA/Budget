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

val Card: ImageVector
    get() {
        if (_Card != null) {
            return _Card!!
        }
        _Card = Builder(
            name = "Card", defaultWidth = 20.0.dp, defaultHeight =
            16.0.dp, viewportWidth = 20.0f, viewportHeight = 16.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(17.0f, 0.0f)
                horizontalLineTo(3.0f)
                curveTo(2.2043f, 0.0f, 1.4413f, 0.3161f, 0.8787f, 0.8787f)
                curveTo(0.3161f, 1.4413f, 0.0f, 2.2043f, 0.0f, 3.0f)
                verticalLineTo(13.0f)
                curveTo(0.0f, 13.7956f, 0.3161f, 14.5587f, 0.8787f, 15.1213f)
                curveTo(1.4413f, 15.6839f, 2.2043f, 16.0f, 3.0f, 16.0f)
                horizontalLineTo(17.0f)
                curveTo(17.7956f, 16.0f, 18.5587f, 15.6839f, 19.1213f, 15.1213f)
                curveTo(19.6839f, 14.5587f, 20.0f, 13.7956f, 20.0f, 13.0f)
                verticalLineTo(3.0f)
                curveTo(20.0f, 2.2043f, 19.6839f, 1.4413f, 19.1213f, 0.8787f)
                curveTo(18.5587f, 0.3161f, 17.7956f, 0.0f, 17.0f, 0.0f)
                close()
                moveTo(18.5f, 13.0f)
                curveTo(18.4995f, 13.3977f, 18.3414f, 13.779f, 18.0602f, 14.0602f)
                curveTo(17.779f, 14.3414f, 17.3977f, 14.4995f, 17.0f, 14.5f)
                horizontalLineTo(3.0f)
                curveTo(2.6023f, 14.4995f, 2.221f, 14.3414f, 1.9398f, 14.0602f)
                curveTo(1.6586f, 13.779f, 1.5005f, 13.3977f, 1.5f, 13.0f)
                verticalLineTo(6.5f)
                horizontalLineTo(18.5f)
                verticalLineTo(13.0f)
                close()
                moveTo(1.5f, 5.0f)
                verticalLineTo(3.0f)
                curveTo(1.5005f, 2.6023f, 1.6586f, 2.221f, 1.9398f, 1.9398f)
                curveTo(2.221f, 1.6586f, 2.6023f, 1.5005f, 3.0f, 1.5f)
                horizontalLineTo(17.0f)
                curveTo(17.3977f, 1.5005f, 17.779f, 1.6586f, 18.0602f, 1.9398f)
                curveTo(18.3414f, 2.221f, 18.4995f, 2.6023f, 18.5f, 3.0f)
                verticalLineTo(5.0f)
                horizontalLineTo(1.5f)
                close()
            }
        }
            .build()
        return _Card!!
    }

private var _Card: ImageVector? = null
