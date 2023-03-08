package ru.bysoft.budget.auth.presentation.screen

import android.app.Activity
import android.view.WindowManager
import android.widget.Toast
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.bysoft.budget.auth.AuthViewModel
import ru.bysoft.budget.auth.IAuthViewModel
import ru.bysoft.budget.auth.R
import ru.bysoft.budget.auth.presentation.entity.AuthActionType
import ru.bysoft.budget.auth.presentation.entity.AuthState
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.budget.uikit.components.textfield.TextFieldState
import ru.bysoft.budget.uikit.styles.UiKitStyles

@Composable
fun AuthScreen() {
    val viewModel: IAuthViewModel = hiltViewModel<AuthViewModel>()

    (LocalContext.current as Activity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    val state = viewModel.state.collectAsState().value

    Scaffold {
        AuthSuccessScreen(it, state, viewModel)
    }
}

@Composable
@OptIn(ExperimentalTextApi::class)
private fun AuthSuccessScreen(
    it: PaddingValues,
    state: AuthState,
    viewModel: IAuthViewModel
) {
    Column(
        modifier = Modifier
            .padding(it)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val welcomeText =
            if (state.type == AuthActionType.SIGN_IN) stringResource(R.string.auth_main_sign_in_welcome_text)
            else stringResource(R.string.auth_main_sign_up_welcome_text)
        Text(
            text = welcomeText,
            style = UiKitStyles.H2,
            modifier = Modifier.padding(30.dp)
        )
        Text(
            text = stringResource(R.string.auth_welcome_text),
            style = UiKitStyles.Body2,
            modifier = Modifier.padding(horizontal = 30.dp)
        )

        val focusRequesterName = remember { FocusRequester() }
        val focusRequesterEmail = remember { FocusRequester() }

        if (state.type == AuthActionType.SIGN_UP) {
            AuthTextField(
                state.name,
                viewModel::setNewName,
                stringResource(R.string.your_name_label),
                stringResource(state.name.errorText ?: R.string.empty_text),
                KeyboardType.Text,
                Modifier.focusRequester(focusRequesterName),
                viewModel::setNewName,
            )
        }

        state.toastText?.let {
            val context = LocalContext.current
            LaunchedEffect(Unit) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

        AuthTextField(
            state.email,
            viewModel::setNewEmail,
            stringResource(R.string.your_email_label),
            stringResource(state.email.errorText ?: R.string.empty_text),
            KeyboardType.Email,
            modifier = Modifier.focusRequester(focusRequesterEmail),
            viewModel::setNewEmail,
        )
        LaunchedEffect(state.type) {
            if (state.type == AuthActionType.SIGN_IN) {
                focusRequesterEmail.requestFocus()
            } else {
                focusRequesterEmail.freeFocus()
                focusRequesterName.requestFocus()
            }
        }


        AuthTextField(
            state.password,
            viewModel::setNewPassword,
            stringResource(R.string.password_label),
            stringResource(state.password.errorText ?: R.string.empty_text),
            KeyboardType.Password,
            onNotFocused = viewModel::setNewPassword
        )


        val simpleText =
            if (state.type == AuthActionType.SIGN_IN) stringResource(R.string.invite_sign_in_text)
            else stringResource(R.string.invite_sign_up_text)

        val linkText =
            if (state.type == AuthActionType.SIGN_IN) stringResource(R.string.invite_sign_in_link)
            else stringResource(R.string.invite_sign_up_link)


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
            if (state.type == AuthActionType.SIGN_IN) stringResource(R.string.auth_btn_sign_in_text)
            else stringResource(R.string.auth_btn_sign_in_text)

        Box(
            Modifier
                .padding(vertical = 26.dp)
                .height(35.dp),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = UiKitColors.colors.grey,
                )
            } else {
                UiKitButton(
                    info = UiKitButtonInfo(text = btnText, type = ButtonType.LARGE),
                    enabled = state.isButtonEnabled,
                    onClick = { viewModel.onButtonClick(state.type) }
                )
            }
        }

    }
}

@Composable
private fun AuthTextField(
    state: TextFieldState,
    onTextChange: (String) -> Unit,
    label: String,
    errorDescription: String,
    type: KeyboardType,
    modifier: Modifier = Modifier,
    onNotFocused: (lastText: String) -> Unit = {},
) {
    val source = remember { MutableInteractionSource() }

    Column {
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = state.text,
            onValueChange = onTextChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .onFocusChanged { if (!it.isFocused) onNotFocused(state.text) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = type
            ),
            shape = RoundedCornerShape(10.dp),
            colors = UiKitColors.colors.textFieldColors,
            label = { Text(label) },
            isError = state.errorText != null,
            interactionSource = source
        )
        if (state.errorText != null && state.errorText != R.string.empty_text) {
            Text(
                text = errorDescription,
                style = UiKitStyles.Caption,
                color = UiKitColors.colors.red,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

    }


}
