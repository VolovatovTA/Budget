package ru.bysoft.budget.auth.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.budget.uikit.styles.UiKitStyles

@Composable
fun AuthScreen() {

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            Text(
                text = "Добро пожаловать!",
                style = UiKitStyles.H2,
                modifier = Modifier.padding(30.dp)
            )
            Text(
                text = "Чтобы всякие повседневные жуки не съели твой бюджет, надо его спланировать. ",
                style = UiKitStyles.Body2,
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 20.dp)
            )
            val textFielValue = remember { mutableStateOf("+7 ") }
            OutlinedTextField(
                value = textFielValue.value,
                onValueChange = { newText ->
                    textFielValue.value = newText
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 20.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number
                ),
                shape = RoundedCornerShape(10.dp),
                colors = UiKitColors.basicTextFieldColors
            )

            Text(
                text = "Введите ваш номер телефона для регистрации или входа. Для проверки мы отправим вам СМС с кодом",
                style = UiKitStyles.Caption,
                modifier = Modifier.padding(horizontal = 30.dp)
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                UiKitButton(
                    info = UiKitButtonInfo(text = "получить код", type = ButtonType.LARGE),
                    modifier = Modifier.padding(vertical = 26.dp),
                    enabled = false
                )
            }
        }
    }
}