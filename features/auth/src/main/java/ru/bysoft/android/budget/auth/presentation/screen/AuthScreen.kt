package ru.bysoft.android.budget.auth.presentation.screen

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
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
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonSize
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.components.checkbox.UiKitCheckBox
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.halfPadding
import ru.bysoft.android.budget.uikit.styles.padding
import ru.bysoft.android.budget.uikit.styles.quarterPadding
import ru.bysoft.android.budget.uikit.utils.ExpandVertically
import ru.bysoft.android.budget.uikit.utils.duration
import ru.bysoft.android.budget.uikit.components.buttons.UiKitSocialMediaButton
import ru.bysoft.android.budget.uikit.components.devider.UiKitDivider

@Composable
fun AuthScreen() {
    val viewModel = koinViewModel<AuthViewModel>()
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
                viewModel.onGoogleSignInResult(
                    SignInResult(
                        token = null,
                        errorMessage = result.data?.toString()
                    )
                )
            }
        }
    )
    Scaffold(
        backgroundColor = UiKitColors.colors.surface.primary,
        modifier = Modifier.safeDrawingPadding()
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
        withLink(
            LinkAnnotation.Clickable(
                tag = state.type.name,
                styles = TextLinkStyles(style = UiKitTypography.Body2Link()),
            ) {
                viewModel.switchAuthType(
                    if (state.type == AuthActionType.SIGN_IN) AuthActionType.SIGN_UP
                    else AuthActionType.SIGN_IN
                )
            }
        ) {
            append(linkText)
        }
    }
    val btnText =
        if (state.type == AuthActionType.SIGN_IN) stringResource(R.string.auth_btn_sign_in_text)
        else stringResource(R.string.auth_btn_sign_up_text)


    if (state.isLoading) {
        Dialog(onDismissRequest = { }) {
            CircularProgressIndicator(
                color = UiKitColors.colors.primary.`300`,
                strokeCap = StrokeCap.Round,
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

                // TODO add link to terms and conditions when it will be ready
                val text1 = stringResource(id = R.string.agree_with_terms_and_conditions)

                val text4 = stringResource(id = R.string.conditions_text)
                val stringWithTermsAndConditions = buildAnnotatedString {
                    withStyle(UiKitTypography.TextXS.Regular.toSpanStyle()) {
                        append(text1)
                        // LinkAnnotation.Url is opened by Text through LocalUriHandler
                        withLink(
                            LinkAnnotation.Url(
                                url = "https://www.iubenda.com/privacy-policy/52608160",
                                styles = TextLinkStyles(
                                    style = UiKitTypography.Body2Link(UiKitTypography.TextXS.Regular)
                                ),
                            )
                        ) {
                            append(text4)
                        }
                    }
                }
                Text(
                    text = stringWithTermsAndConditions,
                    modifier = Modifier
                        .padding(start = halfPadding)
                        // a tap outside the link toggles the checkbox, as before
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
                text = stringResource(
                    if (state.type == AuthActionType.SIGN_IN) R.string.sign_in_with_google
                    else R.string.sign_up_by_google
                ),
                painterLeftImage = painterResource(id = R.drawable.logo_google),
                isButtonEnabled = state.isGoogleButtonEnabled,
                onClick = {
                    scope.launch {
                        viewModel.setLoading(true)
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

        Text(
            text = annotatedString,
            style = UiKitTypography.TextMD.Regular,
            modifier = Modifier
                .padding(
                    horizontal = padding,
                )
                .align(Alignment.CenterHorizontally),
        )
    }
}

@Preview
@Composable
fun AuthPreview() {
    val viewModel = remember {
        object : IAuthViewModel {
            override fun setLoading(b: Boolean) = Unit
            override val state: MutableStateFlow<AuthState>
                get() = MutableStateFlow(
                    AuthState(
                        isLoading = true,
                        toastText = null,
                        type = AuthActionType.SIGN_IN
                    )
                )

            override fun setNewPassword(password: String) = Unit

            override fun setNewConfirmPassword(password: String) = Unit

            override fun onCheckBoxClicked(value: Boolean) = Unit

            override fun setNewName(name: String) = Unit

            override fun setNewEmail(email: String) = Unit

            override fun onButtonClick(action: AuthActionType) = Unit

            override fun switchAuthType(newType: AuthActionType) {
                state.update { it.copy(type = newType) }
            }

            override fun onGoogleSignInResult(account: SignInResult) = Unit

        }
    }
    val state = viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
    ) {
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

