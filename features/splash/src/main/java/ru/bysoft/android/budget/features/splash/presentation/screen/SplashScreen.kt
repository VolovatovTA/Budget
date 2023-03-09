package ru.bysoft.android.budget.features.splash.presentation.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.icons.another.logo
import ru.bysoft.android.budget.features.splash.ISplashViewModel
import ru.bysoft.android.budget.features.splash.SplashViewModel

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

    Box(
        modifier = Modifier.fillMaxSize().background(UiKitColors.colors.light),
        contentAlignment = Alignment.Center
    ) {

        val scale by animateFloatAsState(
            targetValue = if (animatedState.value % 2 == 0) 1f else 0.8f,
            animationSpec = tween(1000),
            finishedListener = {
                animatedState.value++
                if (animatedState.value >= 1)
                    viewModel.onAnimationFinished()
            }
        )

        Icon(
            imageVector = logo,
            contentDescription = null,
            tint = UiKitColors.colors.dark,
            modifier = Modifier
                .height(101.dp * scale)
                .fillMaxSize()
        )

    }

}