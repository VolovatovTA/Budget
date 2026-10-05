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

val Rest: ImageVector
    get() {
        if (_Rest != null) {
            return _Rest!!
        }
        _Rest = Builder(
            name = "Rest", defaultWidth = 20.0.dp, defaultHeight =
            13.0.dp, viewportWidth = 20.0f, viewportHeight = 13.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(5.55f, 7.1f)
                curveTo(5.9555f, 7.1f, 6.3518f, 6.9798f, 6.6889f, 6.7545f)
                curveTo(7.026f, 6.5293f, 7.2888f, 6.2091f, 7.444f, 5.8345f)
                curveTo(7.5991f, 5.4599f, 7.6397f, 5.0477f, 7.5606f, 4.6501f)
                curveTo(7.4815f, 4.2524f, 7.2863f, 3.8871f, 6.9996f, 3.6004f)
                curveTo(6.7129f, 3.3137f, 6.3476f, 3.1185f, 5.9499f, 3.0394f)
                curveTo(5.5523f, 2.9603f, 5.1401f, 3.0009f, 4.7655f, 3.156f)
                curveTo(4.3909f, 3.3112f, 4.0707f, 3.574f, 3.8455f, 3.9111f)
                curveTo(3.6202f, 4.2482f, 3.5f, 4.6445f, 3.5f, 5.05f)
                curveTo(3.5f, 5.5937f, 3.716f, 6.1151f, 4.1004f, 6.4996f)
                curveTo(4.4849f, 6.884f, 5.0063f, 7.1f, 5.55f, 7.1f)
                close()
                moveTo(5.55f, 4.2f)
                curveTo(5.7181f, 4.2f, 5.8825f, 4.2498f, 6.0222f, 4.3432f)
                curveTo(6.162f, 4.4366f, 6.271f, 4.5694f, 6.3353f, 4.7247f)
                curveTo(6.3996f, 4.88f, 6.4165f, 5.0509f, 6.3837f, 5.2158f)
                curveTo(6.3509f, 5.3807f, 6.2699f, 5.5322f, 6.151f, 5.651f)
                curveTo(6.0322f, 5.7699f, 5.8807f, 5.8509f, 5.7158f, 5.8837f)
                curveTo(5.5509f, 5.9165f, 5.38f, 5.8996f, 5.2247f, 5.8353f)
                curveTo(5.0694f, 5.771f, 4.9366f, 5.662f, 4.8432f, 5.5222f)
                curveTo(4.7498f, 5.3825f, 4.7f, 5.2181f, 4.7f, 5.05f)
                curveTo(4.7003f, 4.8246f, 4.7899f, 4.6086f, 4.9493f, 4.4493f)
                curveTo(5.1086f, 4.2899f, 5.3246f, 4.2003f, 5.55f, 4.2f)
                close()
                moveTo(16.0f, 3.0f)
                horizontalLineTo(9.0f)
                verticalLineTo(8.5f)
                horizontalLineTo(1.5f)
                verticalLineTo(0.0f)
                horizontalLineTo(0.0f)
                verticalLineTo(13.0f)
                horizontalLineTo(1.5f)
                verticalLineTo(10.0f)
                horizontalLineTo(18.5f)
                verticalLineTo(13.0f)
                horizontalLineTo(20.0f)
                verticalLineTo(7.0f)
                curveTo(20.0f, 5.9391f, 19.5786f, 4.9217f, 18.8284f, 4.1716f)
                curveTo(18.0783f, 3.4214f, 17.0609f, 3.0f, 16.0f, 3.0f)
                close()
                moveTo(18.5f, 8.5f)
                horizontalLineTo(10.5f)
                verticalLineTo(4.5f)
                horizontalLineTo(16.0f)
                curveTo(16.6628f, 4.5007f, 17.2983f, 4.7644f, 17.7669f, 5.2331f)
                curveTo(18.2356f, 5.7017f, 18.4993f, 6.3372f, 18.5f, 7.0f)
                verticalLineTo(8.5f)
                close()
            }
        }
            .build()
        return _Rest!!
    }

private var _Rest: ImageVector? = null
