package ru.bysoft.android.budget.uikit.icons.another

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Wallet: ImageVector
    get() {
        if (_wallet != null) {
            return _wallet!!
        }
        _wallet = Builder(
            name = "Wallet", defaultWidth = 20.0.dp, defaultHeight = 16.0.dp,
            viewportWidth = 20.0f, viewportHeight = 16.0f
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
                moveTo(18.5f, 10.5f)
                horizontalLineTo(15.0f)
                curveTo(14.337f, 10.5f, 13.7011f, 10.2366f, 13.2322f, 9.7678f)
                curveTo(12.7634f, 9.2989f, 12.5f, 8.663f, 12.5f, 8.0f)
                curveTo(12.5f, 7.337f, 12.7634f, 6.7011f, 13.2322f, 6.2322f)
                curveTo(13.7011f, 5.7634f, 14.337f, 5.5f, 15.0f, 5.5f)
                horizontalLineTo(18.5f)
                verticalLineTo(10.5f)
                close()
                moveTo(18.5f, 4.0f)
                horizontalLineTo(15.0f)
                curveTo(13.9391f, 4.0f, 12.9217f, 4.4214f, 12.1716f, 5.1716f)
                curveTo(11.4214f, 5.9217f, 11.0f, 6.9391f, 11.0f, 8.0f)
                curveTo(11.0f, 9.0609f, 11.4214f, 10.0783f, 12.1716f, 10.8284f)
                curveTo(12.9217f, 11.5786f, 13.9391f, 12.0f, 15.0f, 12.0f)
                horizontalLineTo(18.5f)
                verticalLineTo(13.0f)
                curveTo(18.4995f, 13.3977f, 18.3414f, 13.779f, 18.0602f, 14.0602f)
                curveTo(17.779f, 14.3414f, 17.3977f, 14.4995f, 17.0f, 14.5f)
                horizontalLineTo(3.0f)
                curveTo(2.6023f, 14.4995f, 2.221f, 14.3414f, 1.9398f, 14.0602f)
                curveTo(1.6586f, 13.779f, 1.5005f, 13.3977f, 1.5f, 13.0f)
                verticalLineTo(3.0f)
                curveTo(1.5005f, 2.6023f, 1.6586f, 2.221f, 1.9398f, 1.9398f)
                curveTo(2.221f, 1.6586f, 2.6023f, 1.5005f, 3.0f, 1.5f)
                horizontalLineTo(17.0f)
                curveTo(17.3977f, 1.5005f, 17.779f, 1.6586f, 18.0602f, 1.9398f)
                curveTo(18.3414f, 2.221f, 18.4995f, 2.6023f, 18.5f, 3.0f)
                verticalLineTo(4.0f)
                close()
                moveTo(14.1f, 8.0f)
                curveTo(14.1f, 8.178f, 14.1528f, 8.352f, 14.2517f, 8.5f)
                curveTo(14.3506f, 8.648f, 14.4911f, 8.7634f, 14.6556f, 8.8315f)
                curveTo(14.82f, 8.8996f, 15.001f, 8.9174f, 15.1756f, 8.8827f)
                curveTo(15.3502f, 8.848f, 15.5105f, 8.7623f, 15.6364f, 8.6364f)
                curveTo(15.7623f, 8.5105f, 15.848f, 8.3502f, 15.8827f, 8.1756f)
                curveTo(15.9174f, 8.001f, 15.8996f, 7.82f, 15.8315f, 7.6556f)
                curveTo(15.7634f, 7.4911f, 15.648f, 7.3506f, 15.5f, 7.2517f)
                curveTo(15.352f, 7.1528f, 15.178f, 7.1f, 15.0f, 7.1f)
                curveTo(14.7613f, 7.1f, 14.5324f, 7.1948f, 14.3636f, 7.3636f)
                curveTo(14.1948f, 7.5324f, 14.1f, 7.7613f, 14.1f, 8.0f)
                close()
            }
        }
            .build()
        return _wallet!!
    }

private var _wallet: ImageVector? = null
