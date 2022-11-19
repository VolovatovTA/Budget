package ru.bysoft.budget.auth.presentation.screen

import android.app.Activity
import android.util.Log
import android.view.WindowManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.bysoft.budget.auth.data.TAG
import ru.bysoft.budget.auth.presentation.IAuthViewModel
import ru.bysoft.budget.auth.presentation.entity.AuthActionType
import ru.bysoft.budget.auth.presentation.entity.AuthState
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.budget.uikit.components.textfield.TextFieldState
import ru.bysoft.budget.uikit.styles.UiKitStyles

@OptIn(ExperimentalTextApi::class)
@Composable
fun AuthScreen(
    navHostController: NavHostController,
    viewModel: IAuthViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.init(navHostController)
    }
    (LocalContext.current as Activity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    val state = viewModel.state.collectAsState().value
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val welcomeText =
                if (state.type == AuthActionType.SIGN_IN) "С возвращением!" else "Добро пожаловать!"
            Text(
                text = welcomeText,
                style = UiKitStyles.H2,
                modifier = Modifier.padding(30.dp)
            )
            Text(
                text = "Чтобы всякие повседневные жуки не съели твой бюджет, надо его спланировать. ",
                style = UiKitStyles.Body2,
                modifier = Modifier.padding(horizontal = 30.dp)
            )

            if (state.type == AuthActionType.SIGN_UP) {
                val nameErrorText = ""
                AuthTextField(
                    state.name, viewModel::setNewName, "Ваше имя", nameErrorText,
                    KeyboardType.Text
                )
            }

            val emailErrorText = "Неверный формат email"
            AuthTextField(
                state.email, viewModel::setNewEmail, "Email", emailErrorText,
                KeyboardType.Email
            )

            val passwordErrorText = "Пароль должен содержать как минимум 6 символов"
            AuthTextField(
                state.password,
                viewModel::setNewPassword,
                "Пароль",
                passwordErrorText,
                KeyboardType.Password
            )

            val simpleText =
                if (state.type == AuthActionType.SIGN_IN)
                    "Введите вашу почту и пароль для входа. Если у вас ещё нет аккаунта, "
                else
                    "Введите ваши данные для регистрации. Или, если они у вас уже есть, "
            val linkText =
                if (state.type == AuthActionType.SIGN_IN)
                    "зарегестрируйтесь"
                else
                    "войдите"

            val annotatedString = buildAnnotatedString {
                append(simpleText)

                withStyle(style = UiKitStyles.Body2Link) {
                    withAnnotation(
                        tag = "",
                        annotation = state.type.name
                    ) {
                        append(linkText)
                    }
                }
            }
            ClickableText(
                text = annotatedString,
                style = UiKitStyles.Body2,
                modifier = Modifier.padding(horizontal = 30.dp),
                onClick = { position ->
                    val annotation =
                        annotatedString.getStringAnnotations(position, position).firstOrNull()?.item
                            ?: ""
                    val newType = when (annotation) {
                        AuthActionType.SIGN_IN.name -> AuthActionType.SIGN_UP
                        AuthActionType.SIGN_UP.name -> AuthActionType.SIGN_IN
                        else -> null
                    }
                    newType?.let {
                        viewModel.switchAuthType(newType)
                    }
                }
            )

            val btnText =
                if (state.type == AuthActionType.SIGN_IN) "войти" else "зарегестрироваться"

            UiKitButton(
                info = UiKitButtonInfo(text = btnText, type = ButtonType.LARGE),
                modifier = Modifier.padding(vertical = 26.dp),
                enabled = state.isButtonEnabled,
                onClick = { viewModel.onButtonClick(state.type) }
            )
        }
    }
}

@Composable
private fun AuthTextField(
    state: TextFieldState,
    onTextChange: (String) -> Unit,
    label: String,
    errorDescription: String,
    type: KeyboardType
) {
    Column {
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = state.text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = type
            ),
            shape = RoundedCornerShape(10.dp),
            colors = UiKitColors.basicTextFieldColors,
            label = { Text(label) },
            isError = state.isError
        )
        if (state.isError) {
            Text(
                text = errorDescription,
                style = UiKitStyles.Caption,
                color = UiKitColors.red1,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

    }


}
