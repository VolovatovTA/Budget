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

val Person: ImageVector
    get() {
        if (_Person != null) {
            return _Person!!
        }
        _Person = Builder(
            name = "Person", defaultWidth = 16.0.dp, defaultHeight =
            19.0.dp, viewportWidth = 16.0f, viewportHeight = 19.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(8.0f, 8.0f)
                curveTo(8.7747f, 8.0f, 9.5319f, 7.7703f, 10.176f, 7.34f)
                curveTo(10.8201f, 6.9096f, 11.3222f, 6.2979f, 11.6186f, 5.5823f)
                curveTo(11.9151f, 4.8666f, 11.9927f, 4.0791f, 11.8416f, 3.3193f)
                curveTo(11.6904f, 2.5595f, 11.3174f, 1.8616f, 10.7697f, 1.3138f)
                curveTo(10.2219f, 0.7661f, 9.524f, 0.393f, 8.7642f, 0.2419f)
                curveTo(8.0045f, 0.0908f, 7.217f, 0.1683f, 6.5013f, 0.4648f)
                curveTo(5.7856f, 0.7612f, 5.1739f, 1.2632f, 4.7435f, 1.9073f)
                curveTo(4.3131f, 2.5514f, 4.0834f, 3.3087f, 4.0834f, 4.0833f)
                curveTo(4.0846f, 5.1217f, 4.4976f, 6.1172f, 5.2319f, 6.8515f)
                curveTo(5.9661f, 7.5858f, 6.9616f, 7.9988f, 8.0f, 8.0f)
                close()
                moveTo(8.0f, 1.6666f)
                curveTo(8.478f, 1.6666f, 8.9452f, 1.8083f, 9.3427f, 2.0739f)
                curveTo(9.7401f, 2.3394f, 10.0499f, 2.7168f, 10.2328f, 3.1584f)
                curveTo(10.4157f, 3.6f, 10.4636f, 4.0859f, 10.3704f, 4.5547f)
                curveTo(10.2771f, 5.0235f, 10.047f, 5.4542f, 9.709f, 5.7922f)
                curveTo(9.371f, 6.1301f, 8.9404f, 6.3603f, 8.4716f, 6.4536f)
                curveTo(8.0028f, 6.5468f, 7.5169f, 6.499f, 7.0753f, 6.3161f)
                curveTo(6.6337f, 6.1332f, 6.2562f, 5.8234f, 5.9907f, 5.426f)
                curveTo(5.7251f, 5.0286f, 5.5834f, 4.5613f, 5.5834f, 4.0833f)
                curveTo(5.5841f, 3.4426f, 5.839f, 2.8284f, 6.292f, 2.3753f)
                curveTo(6.7451f, 1.9223f, 7.3593f, 1.6674f, 8.0f, 1.6666f)
                close()
                moveTo(15.707f, 13.3978f)
                curveTo(13.6615f, 11.3567f, 10.8897f, 10.2103f, 8.0f, 10.2103f)
                curveTo(5.1103f, 10.2103f, 2.3386f, 11.3567f, 0.293f, 13.3978f)
                lineTo(0.0f, 13.6907f)
                verticalLineTo(19.0f)
                horizontalLineTo(16.0f)
                verticalLineTo(13.6907f)
                lineTo(15.707f, 13.3978f)
                close()
                moveTo(14.5f, 17.5f)
                horizontalLineTo(1.5f)
                verticalLineTo(14.3206f)
                curveTo(1.5271f, 14.2958f, 1.6031f, 14.2225f, 1.6306f, 14.1984f)
                curveTo(3.3659f, 12.5986f, 5.6397f, 11.7103f, 8.0f, 11.7103f)
                curveTo(10.3603f, 11.7103f, 12.6341f, 12.5986f, 14.3694f, 14.1984f)
                curveTo(14.3969f, 14.2225f, 14.473f, 14.2958f, 14.5f, 14.3206f)
                verticalLineTo(17.5f)
                close()
            }
        }
            .build()
        return _Person!!
    }

private var _Person: ImageVector? = null
