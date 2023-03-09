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

val Beach: ImageVector
    get() {
        if (_Beach != null) {
            return _Beach!!
        }
        _Beach = Builder(
            name = "Beach", defaultWidth = 21.0.dp, defaultHeight =
            20.0.dp, viewportWidth = 21.0f, viewportHeight = 20.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(14.0752f, 18.5513f)
                lineTo(11.7323f, 9.8058f)
                lineTo(20.7941f, 7.3777f)
                curveTo(20.0653f, 5.2897f, 18.7222f, 3.4709f, 16.9412f, 2.1599f)
                curveTo(15.1601f, 0.8489f, 13.0243f, 0.1071f, 10.8141f, 0.0317f)
                curveTo(9.9927f, 0.0317f, 9.1748f, 0.1396f, 8.3814f, 0.3527f)
                curveTo(3.2914f, 1.7164f, 0.236f, 7.0646f, 1.1442f, 12.6427f)
                lineTo(6.2449f, 11.276f)
                lineTo(10.2832f, 10.1939f)
                lineTo(12.506f, 18.491f)
                curveTo(9.272f, 18.4121f, 6.0506f, 18.9234f, 3.0f, 20.0f)
                horizontalLineTo(20.9442f)
                curveTo(18.725f, 19.2099f, 16.409f, 18.724f, 14.0593f, 18.5555f)
                lineTo(14.0752f, 18.5513f)
                close()
                moveTo(8.77f, 1.8015f)
                curveTo(8.8324f, 1.785f, 8.8967f, 1.7768f, 8.9612f, 1.7772f)
                curveTo(10.1232f, 1.7772f, 12.2112f, 3.8995f, 13.722f, 7.7198f)
                lineTo(7.4411f, 9.4027f)
                curveTo(6.7988f, 5.0839f, 7.6694f, 2.0964f, 8.77f, 1.8015f)
                close()
                moveTo(18.6764f, 6.3923f)
                lineTo(15.1857f, 7.3276f)
                curveTo(14.4377f, 5.0683f, 13.0623f, 3.0685f, 11.22f, 1.5618f)
                curveTo(12.767f, 1.6815f, 14.2595f, 2.1861f, 15.5618f, 3.0297f)
                curveTo(16.864f, 3.8734f, 17.9346f, 5.0293f, 18.6761f, 6.3923f)
                horizontalLineTo(18.6764f)
                close()
                moveTo(2.4837f, 10.731f)
                curveTo(2.4392f, 9.1766f, 2.7863f, 7.636f, 3.493f, 6.2508f)
                curveTo(4.1996f, 4.8656f, 5.2432f, 3.6803f, 6.5278f, 2.804f)
                curveTo(5.689f, 5.0358f, 5.498f, 7.4593f, 5.9768f, 9.795f)
                lineTo(2.4837f, 10.731f)
                close()
            }
        }
            .build()
        return _Beach!!
    }

private var _Beach: ImageVector? = null
