package ru.bysoft.budget.splash.presentation.screen

import android.graphics.drawable.Icon
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.bysoft.budget.splash.ISplashViewModel
import ru.bysoft.budget.splash.SplashViewModel
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.icons.another.logo

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