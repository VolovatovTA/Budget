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

val Search: ImageVector
    get() {
        if (_Search != null) {
            return _Search!!
        }
        _Search = Builder(
            name = "Search", defaultWidth = 22.0.dp, defaultHeight =
            22.0.dp, viewportWidth = 22.0f, viewportHeight = 22.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(21.0301f, 19.97f)
                lineTo(15.1616f, 14.1016f)
                curveTo(16.4735f, 12.5171f, 17.1248f, 10.4881f, 16.9802f, 8.4361f)
                curveTo(16.8356f, 6.384f, 15.9062f, 4.4665f, 14.3849f, 3.0817f)
                curveTo(12.8637f, 1.6969f, 10.8675f, 0.9511f, 8.811f, 0.9994f)
                curveTo(6.7544f, 1.0476f, 4.7954f, 1.8861f, 3.3407f, 3.3407f)
                curveTo(1.8861f, 4.7954f, 1.0476f, 6.7544f, 0.9994f, 8.811f)
                curveTo(0.9511f, 10.8675f, 1.6969f, 12.8637f, 3.0817f, 14.3849f)
                curveTo(4.4665f, 15.9062f, 6.384f, 16.8356f, 8.4361f, 16.9802f)
                curveTo(10.4881f, 17.1248f, 12.5171f, 16.4735f, 14.1016f, 15.1616f)
                lineTo(19.9701f, 21.03f)
                lineTo(21.0301f, 19.97f)
                close()
                moveTo(2.5001f, 9.0f)
                curveTo(2.5001f, 7.7144f, 2.8813f, 6.4577f, 3.5955f, 5.3888f)
                curveTo(4.3097f, 4.3198f, 5.3249f, 3.4867f, 6.5126f, 2.9947f)
                curveTo(7.7003f, 2.5028f, 9.0073f, 2.374f, 10.2681f, 2.6249f)
                curveTo(11.529f, 2.8757f, 12.6872f, 3.4947f, 13.5963f, 4.4038f)
                curveTo(14.5053f, 5.3128f, 15.1244f, 6.471f, 15.3752f, 7.7319f)
                curveTo(15.626f, 8.9928f, 15.4972f, 10.2997f, 15.0053f, 11.4874f)
                curveTo(14.5133f, 12.6751f, 13.6802f, 13.6903f, 12.6113f, 14.4045f)
                curveTo(11.5423f, 15.1187f, 10.2856f, 15.5f, 9.0001f, 15.5f)
                curveTo(7.2768f, 15.498f, 5.6246f, 14.8126f, 4.406f, 13.594f)
                curveTo(3.1875f, 12.3754f, 2.502f, 10.7233f, 2.5001f, 9.0f)
                close()
            }
        }
            .build()
        return _Search!!
    }

private var _Search: ImageVector? = null
