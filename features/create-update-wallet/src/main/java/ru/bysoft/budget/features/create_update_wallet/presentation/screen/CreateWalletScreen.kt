package ru.bysoft.budget.features.create_update_wallet.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.bysoft.budget.features.create_update_wallet.CreateWalletViewModel
import ru.bysoft.budget.features.create_update_wallet.ICreateWalletViewModel
import ru.bysoft.budget.features.create_update_wallet.presentation.entity.CreateWalletState
import ru.bysoft.budget.features.create_wallet.R
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.budget.uikit.components.currecyfield.UiKitCurrencyPopUp
import ru.bysoft.budget.uikit.components.textfield.TextFieldState
import ru.bysoft.budget.uikit.styles.UiKitStyles

@Composable
fun CreateWalletScreen() {
    val viewModel: ICreateWalletViewModel = hiltViewModel<CreateWalletViewModel>()
    val state = viewModel.state.collectAsState().value
    state.toastText?.let {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    Scaffold {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            CreateWalletTextField(
                state = state.nameTextState,
                onTextChange = viewModel::onNameChanged,
                label = stringResource(R.string.wallet_name_text),
                type = KeyboardType.Text,
                errorDescription = stringResource(state.nameTextState.errorText ?: ru.bysoft.budget.R.string.empty_text)
            )
            CreateWalletTextField(
                state = state.balanceTextState,
                onTextChange = viewModel::onBalanceChanged,
                label = stringResource(R.string.wallet_balance_text),
                type = KeyboardType.Number,
                errorDescription = stringResource(state.balanceTextState.errorText ?: ru.bysoft.budget.R.string.empty_text)
            )
            UiKitCurrencyPopUp(
                state.currencyFieldState,
                viewModel::onCurrencySelected,
                modifier = Modifier.padding(
                    horizontal = 30.dp
                )
            )
            Spacer(modifier = Modifier.height(40.dp))
            ButtonComponent(viewModel, state)
        }
    }
}

@Composable
private fun ButtonComponent(viewModel: ICreateWalletViewModel, state: CreateWalletState) {
    Box(modifier = Modifier.height(50.dp)) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxHeight())
        } else {
            UiKitButton(
                info = UiKitButtonInfo(stringResource(id = R.string.wallet_btn_create_text), type = ButtonType.MEDIUM),
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
        if (state.errorText != null && state.errorText != ru.bysoft.budget.R.string.empty_text) {
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