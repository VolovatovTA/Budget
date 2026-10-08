package ru.bysoft.android.budget.features.create_update_wallet.presentation.screen

import ru.bysoft.android.budget.uikit.icons.UiKitIcons.UiKitIconPack
import ru.bysoft.android.budget.currency.getAvailableCurrency
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import ru.bysoft.android.budget.common.R
import ru.bysoft.android.budget.common.data_entity.CreateWalletErrorData
import ru.bysoft.android.budget.currency.BudgetCurrencyEnum
import ru.bysoft.android.budget.features.create_update_wallet.CreateWalletViewModel
import ru.bysoft.android.budget.features.create_update_wallet.ICreateWalletViewModel
import ru.bysoft.android.budget.features.create_update_wallet.IUpdateWalletViewModel
import ru.bysoft.android.budget.features.create_update_wallet.IWalletScreenController
import ru.bysoft.android.budget.features.create_update_wallet.IWalletViewModel
import ru.bysoft.android.budget.features.create_update_wallet.presentation.entity.ControllerWalletState
import ru.bysoft.android.budget.features.create_update_wallet.presentation.entity.ViewModelWalletState
import ru.bysoft.android.budget.uikit.colors.UiKitColors
import ru.bysoft.android.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonSize
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.components.currencyfield.entity.CurrencyFieldState
import ru.bysoft.android.budget.uikit.components.icon_component.UiKitIconsComponent
import ru.bysoft.android.budget.uikit.components.textfield.TextFieldState
import ru.bysoft.android.budget.uikit.components.textfield.UiKitCurrencyPopUpTextField
import ru.bysoft.android.budget.uikit.components.textfield.UiKitTextFieldWithCurrency
import ru.bysoft.android.budget.uikit.styles.UiKitTypography
import ru.bysoft.android.budget.uikit.styles.corner
import ru.bysoft.android.budget.uikit.styles.halfPadding
import ru.bysoft.android.budget.uikit.styles.padding
import ru.bysoft.android.budget.uikit.templates.topBar
import ru.bysoft.android.budget.uikit.theme.BudgetTheme

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
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(padding),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                    onValueChange = {
                        screenController.onBalanceChanged(it)

                    },
                    label = stringResource(R.string.wallet_balance_text),
                    inputType = KeyboardType.Number,
                    modifier = Modifier
                        .padding(horizontal = padding)
                        .fillMaxWidth(),
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Next)
                    },
                    popUpList = controllerWalletState.currencyFieldState,
                    popUpItem = { t ->
                        t?.let {
                            UiKitCurrencyPopUpTextField(t.displayName, t.flag)
                        }
                    },
                    onSelectPopUpItem = screenController::onCurrencySelected
                )
            }

            Text(
                text = stringResource(R.string.wallet_icon_text),
                style = UiKitTypography.TextSM.Regular,
                modifier = Modifier.padding(horizontal = padding)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = padding)
                    .border(1.dp, UiKitColors.colors.type.medium, RoundedCornerShape(corner))
                    .padding(halfPadding)
            ) {
                UiKitIconsComponent(
                    pack = UiKitIconPack.WALLET,
                    screenController::onIconSelected,
                    controllerWalletState.iconState
                )
            }

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
                color = UiKitColors.colors.feedbackRed.`600`,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewCreateWalletScreen() {
    BudgetTheme {
        val screenController = object : IWalletScreenController {
            override val state: MutableStateFlow<ControllerWalletState>
                get() = MutableStateFlow(
                    ControllerWalletState(
                        nameTextState = TextFieldState(
                            "Wallet name",
                            UiKitStrings.currency_not_selected
                        ),
                        balanceTextState = TextFieldState(),
                        currencyFieldState = CurrencyFieldState(
                            selectedCurrency = BudgetCurrencyEnum.RUB,
                            list = getAvailableCurrency(),
                        ),
                    )
                )

            override fun onNameChanged(name: String) = Unit
            override fun onBalanceChanged(balance: String) = Unit
            override fun onCurrencySelected(currency: BudgetCurrencyEnum) = Unit
            override fun onIconSelected(iconName: String?) = Unit
            override fun showError(errorType: CreateWalletErrorData?) = Unit

        }

        val viewModel = object : ICreateWalletViewModel {
            override fun onBackClick() = Unit
            override fun onButtonClick() = Unit
        }

        CRUDWalletScreen(
            screenController = screenController,
            viewModelWalletState = ViewModelWalletState(),
            viewModel = viewModel
        )
    }

}