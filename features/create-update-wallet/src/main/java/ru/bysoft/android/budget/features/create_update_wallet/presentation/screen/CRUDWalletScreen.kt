package ru.bysoft.android.budget.features.create_update_wallet.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.bysoft.android.budget.common.R
import ru.bysoft.android.budget.features.create_update_wallet.CreateWalletViewModel
import ru.bysoft.android.budget.features.create_update_wallet.IUpdateWalletViewModel
import ru.bysoft.android.budget.features.create_update_wallet.IWalletScreenController
import ru.bysoft.android.budget.features.create_update_wallet.IWalletViewModel
import ru.bysoft.android.budget.features.create_update_wallet.presentation.entity.ViewModelWalletState
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonSize
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.components.icon_component.UiKitIconsComponent
import ru.bysoft.android.budget.uikit.components.textfield.TextFieldState
import ru.bysoft.android.budget.uikit.components.textfield.UiKitCurrencyPopUpTextField
import ru.bysoft.android.budget.uikit.components.textfield.UiKitTextFieldWithCurrency
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.padding
import ru.bysoft.android.budget.uikit.templates.topBar

typealias UiKitStrings = ru.bysoft.android.budget.uikit.R.string

@Composable
fun CRUDWalletScreen(
    screenController: IWalletScreenController,
    viewModelWalletState: ViewModelWalletState,
    viewModel: IWalletViewModel,
    walletId: String? = null
) {
    val controllerWalletState = screenController.state.collectAsState().value
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    viewModelWalletState.toastText?.let {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    LaunchedEffect(Unit) { focusRequester.requestFocus() }
    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        backgroundColor = UiKitColors.colors.surface.primary,
        topBar = topBar(
            title = if (viewModel is CreateWalletViewModel) R.string.wallet_title_create else R.string.wallet_title_update,
            onBackClick = viewModel::onBackClick,
            onClickDelete = walletId?.let {
                { (viewModel as? IUpdateWalletViewModel)?.onWalletDeleteClick(it) }
            }
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            CreateWalletTextField(
                state = controllerWalletState.nameTextState,
                onTextChange = screenController::onNameChanged,
                label = stringResource(R.string.wallet_name_text),
                type = KeyboardType.Text,
                errorDescription = stringResource(
                    controllerWalletState.nameTextState.errorText ?: UiKitStrings.empty_text
                ),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                modifier = Modifier
                    .focusRequester(focusRequester)
            )
            if (viewModel is CreateWalletViewModel) {
                UiKitTextFieldWithCurrency(
                    state = controllerWalletState.balanceTextState,
                    onValueChange = screenController::onBalanceChanged,
                    label = stringResource(R.string.wallet_balance_text),
                    inputType = KeyboardType.Number,
                    modifier = Modifier
                        .padding(horizontal = padding)
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Next)
                    },
                    popUpList = controllerWalletState.currencyFieldState,
                    popUpItem = { t ->
                        t?.let {
                            UiKitCurrencyPopUpTextField(t)
                        }
                    },
                    onSelectPopUpItem = screenController::onCurrencySelected
                )
            }
            UiKitIconsComponent(screenController::onIconSelected, controllerWalletState.iconState)

            Spacer(modifier = Modifier.height(40.dp))
            ButtonComponent(viewModel, viewModelWalletState)
        }
    }
}

@Composable
private fun ButtonComponent(viewModel: IWalletViewModel, state: ViewModelWalletState) {
    Box(modifier = Modifier.height(50.dp)) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxHeight())
        } else {
            UiKitButton(
                info = UiKitButtonInfo(
                    if (viewModel is CreateWalletViewModel) stringResource(id = R.string.wallet_btn_create_text) else stringResource(
                        id = R.string.wallet_btn_update_text
                    ),
                    size = ButtonSize.MEDIUM
                ),
                onClick = viewModel::onButtonClick
            )
        }
    }

}

@Composable
private fun CreateWalletTextField(
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
                .padding(horizontal = padding)
                .onFocusChanged { if (!it.isFocused) onNotFocused(state.text) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = type
            ),
            shape = RoundedCornerShape(10.dp),
            colors = UiKitColors.textField,
            label = { Text(label) },
            isError = state.errorText != null,
            interactionSource = source,
            keyboardActions = keyboardActions
        )
        if (state.errorText != null && state.errorText != UiKitStrings.empty_text) {
            Text(
                text = errorDescription,
                style = UiKitTypography.TextXS.Regular,
                color = UiKitColors.colors.feedbackRed.`1100`,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}