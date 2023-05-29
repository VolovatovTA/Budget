package ru.bysoft.android.budget.features.splash.presentation.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.features.splash.ISplashViewModel
import ru.bysoft.android.budget.features.splash.SplashViewModel
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.icons.another.ItBearsLogo

@Composable
fun SplashScreen() {
    val viewModel: ISplashViewModel = hiltViewModel<SplashViewModel>()
    val animatedState = remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            delay(500)
            animatedState.value++
        }
    }

    SplashContent(animatedState, viewModel::onAnimationFinished)
}

@Preview
@Composable
private fun SplashContent(
    animatedState: MutableState<Int> = mutableStateOf(0),
    viewModel: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(UiKitColors.colors.surface.primary),
        contentAlignment = Alignment.Center
    ) {
        val scale by animateFloatAsState(
            targetValue = if (animatedState.value % 2 == 0) 0.7f else 1f,
            animationSpec = tween(1000),
            finishedListener = {
                animatedState.value++
                if (animatedState.value >= 2)
                    viewModel.invoke()
            }
        )

        Image(
            imageVector = ItBearsLogo,
            contentDescription = null,
            modifier = Modifier
                .height(100.dp * scale)
                .rotate((scale - 0.7f) / 0.3f * 360f)
                .fillMaxSize()
        )
    }
}