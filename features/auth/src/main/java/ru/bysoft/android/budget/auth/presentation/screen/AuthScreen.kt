package ru.bysoft.android.budget.auth.presentation.screen

import android.app.Activity.RESULT_OK
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.bysoft.android.budget.auth.AuthViewModel
import ru.bysoft.android.budget.auth.IAuthViewModel
import ru.bysoft.android.budget.auth.R
import ru.bysoft.android.budget.auth.presentation.GoogleAuthUiClient
import ru.bysoft.android.budget.auth.presentation.SignInResult
import ru.bysoft.android.budget.auth.presentation.components.AuthTextField
import ru.bysoft.android.budget.auth.presentation.entity.AuthActionType
import ru.bysoft.android.budget.auth.presentation.entity.AuthState
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.android.budget.uikit.components.buttons.UiKitSocialMediaButton
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonSize
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.components.checkbox.UiKitCheckBox
import ru.bysoft.android.budget.uikit.components.devider.UiKitDivider
import ru.bysoft.android.budget.uikit.styles.*

@Composable
fun AuthScreen() {
    val viewModel = hiltViewModel<AuthViewModel>()
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    state.toastText?.let {
        LaunchedEffect(Unit) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
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
            if (result.resultCode == RESULT_OK) {
                scope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(result.data!!)
                    viewModel.onGoogleSignInResult(signInResult)
                }
            } else {
                Log.e("timvol", "AuthSuccessScreen: $result")
            }
        }
    )
    Scaffold(
        backgroundColor = UiKitColors.colors.surface.primary,
    ) {
        AuthSuccessScreen(it, state, viewModel, googleAuthUiClient, launcher, scope)
    }
}

@Composable
@OptIn(ExperimentalTextApi::class)
private fun AuthSuccessScreen(
    paddingValues: PaddingValues,
    state: AuthState,
    viewModel: IAuthViewModel,
    googleAuthUiClient: GoogleAuthUiClient?,
    launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>?,
    scope: CoroutineScope
) {

    val focusManager = LocalFocusManager.current
    val focusRequesterName = remember { FocusRequester() }
    val focusRequesterEmail = remember { FocusRequester() }

    val welcomeText =
        if (state.type == AuthActionType.SIGN_IN) stringResource(R.string.auth_main_sign_in_welcome_text)
        else stringResource(R.string.auth_main_sign_up_welcome_text)

    LaunchedEffect(state.type) {
        if (state.type == AuthActionType.SIGN_IN) {
            focusRequesterEmail.requestFocus()
        } else {
            delay(duration.toLong())
            focusRequesterEmail.freeFocus()
            focusRequesterName.requestFocus()
        }
    }

    val simpleText =
        if (state.type == AuthActionType.SIGN_IN) stringResource(R.string.invite_sign_in_text)
        else stringResource(R.string.invite_sign_up_text)

    val linkText =
        if (state.type == AuthActionType.SIGN_IN) stringResource(R.string.invite_sign_in_link)
        else stringResource(R.string.invite_sign_up_link)


    val annotatedString = buildAnnotatedString {
        append(simpleText)

        withStyle(style = UiKitTypography.Body2Link) {
            withAnnotation(
                tag = "",
                annotation = state.type.name
            ) {
                append(linkText)
            }
        }
    }
    val btnText =
        if (state.type == AuthActionType.SIGN_IN) stringResource(R.string.auth_btn_sign_in_text)
        else stringResource(R.string.auth_btn_sign_up_text)


    if (state.isLoading) {
        Dialog(onDismissRequest = { }) {
            CircularProgressIndicator(
                color = UiKitColors.colors.neutral.`1100`,
            )
        }
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(top = padding + halfPadding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
    ) {

        Column {
            Text(
                text = welcomeText,
                style = UiKitTypography.DisplaySM.SemiBold,
                modifier = Modifier.padding(
                    start = padding,
                    end = padding,
                    bottom = quarterPadding
                )
            )
            Text(
                text = stringResource(R.string.auth_welcome_text),
                color = UiKitColors.colors.type.medium,
                style = UiKitTypography.TextLG.Regular,
                modifier = Modifier
                    .padding(horizontal = padding)
            )
        }
        Spacer(modifier = Modifier.height(padding + halfPadding))

        Column(
            modifier = Modifier,
        ) {

            ExpandVertically(state.type == AuthActionType.SIGN_UP) {
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
            ExpandVertically(state.type == AuthActionType.SIGN_UP) {
                Spacer(modifier = Modifier.height(padding))
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
            Spacer(modifier = Modifier.height(padding))

            AuthTextField(
                state = state.password,
                onTextChange = viewModel::setNewPassword,
                label = stringResource(if (state.type == AuthActionType.SIGN_IN) R.string.password_enter_label else R.string.password_create_label),
                errorDescription = stringResource(state.password.errorText ?: R.string.empty_text),
                type = KeyboardType.Password,
                onNotFocused = { },
                keyboardActions = KeyboardActions {
                    if (state.type == AuthActionType.SIGN_IN) focusManager.clearFocus()
                    else focusManager.moveFocus(FocusDirection.Next)
                }
            )

            ExpandVertically(state.type == AuthActionType.SIGN_UP) {
                Spacer(modifier = Modifier.height(padding))
            }
            ExpandVertically(state.type == AuthActionType.SIGN_UP) {
                AuthTextField(
                    state = state.confirmPassword,
                    onTextChange = viewModel::setNewConfirmPassword,
                    label = stringResource(R.string.password_confirm_enter_label),
                    errorDescription = stringResource(
                        state.password.errorText ?: R.string.empty_text
                    ),
                    type = KeyboardType.Password,
                    onNotFocused = { },
                    keyboardActions = KeyboardActions { focusManager.clearFocus() }
                )
            }
        }

        ExpandVertically(state.type == AuthActionType.SIGN_UP) {
            Spacer(Modifier.height(padding + halfPadding))
        }
        ExpandVertically(state.type == AuthActionType.SIGN_UP) {
            Row(
                modifier = Modifier
                    .padding(horizontal = padding)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UiKitCheckBox(
                    checked = state.isCheckBoxChecked,
                    onCheckedChange = viewModel::onCheckBoxClicked,
                    modifier = Modifier
                        .size(20.dp)
                )
                Text(
                    text = stringResource(id = R.string.agree_with_terms_and_conditions),
                    style = UiKitTypography.TextMD.Regular,
                    modifier = Modifier
                        .padding(start = halfPadding)
                        .clickable { viewModel.onCheckBoxClicked(state.isCheckBoxChecked.not()) }
                )
            }
        }

        Spacer(modifier = Modifier.height(padding + halfPadding))

        UiKitButton(
            info = UiKitButtonInfo(text = btnText, size = ButtonSize.MEDIUM),
            isButtonEnabled = state.isButtonEnabled,
            onClick = { viewModel.onButtonClick(state.type) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = padding)
        )
        Spacer(modifier = Modifier.height(padding + halfPadding))

        UiKitDivider(
            text = stringResource(id = R.string.or_continue_with),
            modifier = Modifier.padding(
                horizontal = padding,
            )
        )
        Spacer(modifier = Modifier.height(padding + halfPadding))

        Column(verticalArrangement = Arrangement.spacedBy(halfPadding)) {

            UiKitSocialMediaButton(
                text = stringResource(id = R.string.sign_in_with_google),
                painterLeftImage = painterResource(id = R.drawable.logo_google),
                isButtonEnabled = true,
                onClick = {
                    scope.launch {
                        val signInIntentSender = googleAuthUiClient!!.signIn()
                        launcher!!.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = padding)
            )
        }

        Spacer(modifier = Modifier.height(padding + halfPadding))

        ClickableText(
            text = annotatedString,
            style = UiKitTypography.TextMD.Regular,
            modifier = Modifier
                .padding(
                    horizontal = padding,
                )
                .align(Alignment.CenterHorizontally),
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
    }
}

@Preview
@Composable
fun AuthPreview() {
    val viewModel = remember {
        object : IAuthViewModel {
            val _state = MutableStateFlow(
                AuthState(
                    isLoading = false,
                    toastText = null,
                    type = AuthActionType.SIGN_IN
                )
            )

            override val state: StateFlow<AuthState>
                get() = _state.asStateFlow()

            override fun setNewPassword(password: String) = Unit

            override fun setNewConfirmPassword(password: String) = Unit

            override fun onCheckBoxClicked(value: Boolean) = Unit

            override fun setNewName(name: String) = Unit

            override fun setNewEmail(email: String) = Unit

            override fun onButtonClick(action: AuthActionType) = Unit

            override fun switchAuthType(newType: AuthActionType) {
                _state.update { it.copy(type = newType) }
            }

            override fun onGoogleSignInResult(account: SignInResult) = Unit

        }
    }
    val state = viewModel.state.collectAsState()

    Scaffold {
        AuthSuccessScreen(
            paddingValues = it,
            state = state.value,
            viewModel = viewModel,
            googleAuthUiClient = null,
            launcher = null,
            scope = rememberCoroutineScope()
        )
    }

}

const val duration = 300

@Composable
fun ExpandVertically(visible: Boolean, function: @Composable () -> Unit) {
    val enter = fadeIn(
        animationSpec = tween(delayMillis = duration)
    ) + expandVertically(
        animationSpec = tween(durationMillis = duration)
    )
    val exit = fadeOut(
        animationSpec = tween(durationMillis = duration)
    ) + shrinkVertically(
        animationSpec = tween(delayMillis = duration),
    )

    AnimatedVisibility(
        visible = visible,
        enter = enter,
        exit = exit
    ) {
        function()
    }
}

