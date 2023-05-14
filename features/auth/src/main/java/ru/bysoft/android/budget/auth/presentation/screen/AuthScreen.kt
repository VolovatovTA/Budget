package ru.bysoft.android.budget.auth.presentation.screen

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.auth.AuthViewModel
import ru.bysoft.android.budget.auth.IAuthViewModel
import ru.bysoft.android.budget.auth.R
import ru.bysoft.android.budget.auth.presentation.GoogleAuthUiClient
import ru.bysoft.android.budget.auth.presentation.entity.AuthActionType
import ru.bysoft.android.budget.auth.presentation.entity.AuthState
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.components.textfield.TextFieldState
import ru.bysoft.android.budget.uikit.styles.UiKitStyles

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

        val focusManager = LocalFocusManager.current
        val focusRequesterName = remember { FocusRequester() }
        val focusRequesterEmail = remember { FocusRequester() }

        if (state.type == AuthActionType.SIGN_UP) {
            AuthTextField(
                state = state.name,
                onTextChange = viewModel::setNewName,
                label = stringResource(R.string.your_name_label),
                errorDescription = stringResource(state.name.errorText ?: R.string.empty_text),
                type = KeyboardType.Text,
                modifier = Modifier.focusRequester(focusRequesterName),
                onNotFocused = { },
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                },
            )
        }

        state.toastText?.let {
            val context = LocalContext.current
            LaunchedEffect(Unit) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

        AuthTextField(
            state = state.email,
            onTextChange = viewModel::setNewEmail,
            label = stringResource(R.string.your_email_label),
            errorDescription = stringResource(state.email.errorText ?: R.string.empty_text),
            type = KeyboardType.Email,
            modifier = Modifier.focusRequester(focusRequesterEmail),
            onNotFocused = { },
            keyboardActions = KeyboardActions {
                focusManager.moveFocus(FocusDirection.Next)
            },
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
            state = state.password,
            onTextChange = viewModel::setNewPassword,
            label = stringResource(if (state.type == AuthActionType.SIGN_IN) R.string.password_enter_label else R.string.password_create_label),
            errorDescription = stringResource(state.password.errorText ?: R.string.empty_text),
            type = KeyboardType.Password,
            onNotFocused = { },
            keyboardActions = KeyboardActions { focusManager.clearFocus() }
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
            else stringResource(R.string.auth_btn_sign_up_text)

        val context = LocalContext.current
        val googleAuthUiClient = remember {
            GoogleAuthUiClient(
                context = context,
                oneTapClient = Identity.getSignInClient(context)
            )
        }
        val scope = rememberCoroutineScope()
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = { result ->
                if(result.resultCode == RESULT_OK) {
                    scope.launch {
                        val signInResult = googleAuthUiClient.signInWithIntent(
                            intent = result.data ?: return@launch
                        )
                        viewModel.onGoogleSignInResult(signInResult)
                    }
                }
            }
        )

        Box(
            Modifier
                .padding(vertical = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = UiKitColors.colors.grey,
                )
            } else {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Button(
                        onClick = {
                            scope.launch {
                                val signInIntentSender = googleAuthUiClient.signIn()
                                launcher.launch(
                                    IntentSenderRequest.Builder(
                                        signInIntentSender ?: return@launch
                                    ).build()
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = UiKitColors.colors.dark40,
                            contentColor = UiKitColors.colors.dark
                        )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_google),
                            contentDescription = null
                        )
                        Text(text = stringResource(id = R.string.sign_in_with_google), modifier = Modifier.padding(6.dp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    UiKitButton(
                        info = UiKitButtonInfo(text = btnText, type = ButtonType.LARGE),
                        enabled = state.isButtonEnabled,
                        onClick = { viewModel.onButtonClick(state.type) }
                    )
                }
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
    keyboardActions: KeyboardActions,
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
            interactionSource = source,
            keyboardActions = keyboardActions
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
