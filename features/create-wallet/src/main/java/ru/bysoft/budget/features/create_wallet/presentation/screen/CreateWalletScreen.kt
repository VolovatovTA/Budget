package ru.bysoft.budget.features.create_wallet.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.bysoft.budget.common.util.BudgetCurrency
import ru.bysoft.budget.features.create_wallet.CreateWalletViewModel
import ru.bysoft.budget.features.create_wallet.ICreateWalletViewModel
import ru.bysoft.budget.features.create_wallet.presentation.entity.CreateWalletState
import ru.bysoft.budget.features.create_wallet.presentation.entity.CurrencyField
import ru.bysoft.budget.uikit.colors.UiKitColors
import ru.bysoft.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.budget.uikit.components.textfield.TextFieldState
import ru.bysoft.budget.uikit.components.textfield.UiKitTextField
import ru.bysoft.budget.uikit.styles.UiKitStyles
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateWalletScreen() {
    val viewModel: ICreateWalletViewModel = hiltViewModel<CreateWalletViewModel>()
    val state = viewModel.state.collectAsState().value
    Scaffold {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            NameTextField(state.nameTextState, viewModel::onNameChanged)
            BalanceTextField(state.balanceTextState, viewModel::onBalanceChanged)
            CurrencyPopUp(state.currencyFieldState, viewModel::onCurrencySelected)
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
                info = UiKitButtonInfo("Создать кошелёк", type = ButtonType.MEDIUM),
                onClick = viewModel::onButtonClick
            )
        }
    }

}

@Composable
fun NameTextField(info: TextFieldState, onNameChanged: (String) -> Unit) {
    UiKitTextField(
        state = info,
        onValueChange = onNameChanged,
        label = "Имя нового кошелька",
        inputType = KeyboardType.Text
    )
}

@Composable
fun BalanceTextField(info: TextFieldState, onBalanceChanged: (String) -> Unit) {
    UiKitTextField(
        state = info,
        onValueChange = onBalanceChanged,
        label = "Баланс нового кошелька",
        inputType = KeyboardType.Number
    )
}

@Composable
fun CurrencyPopUp(info: CurrencyField, onNameChanged: (BudgetCurrency) -> Unit) {
    val showMenu = remember { mutableStateOf(false) }
    Spacer(modifier = Modifier.height(20.dp))

    Column(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .clickable { showMenu.value = !showMenu.value }
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, UiKitColors.colors.col5),
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(),
            color = Color.Transparent
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(15.dp))
                val text = if (info.selectedCurrency != null) {
                    "${info.selectedCurrency.displayName} ${Currency.getInstance(info.selectedCurrency.iso4217).displayName}"
                } else {
                    "Выберете валюту"
                }

                Text(
                    text = text,
                    style = UiKitStyles.Body2,
                    color = UiKitColors.colors.col5,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = if (showMenu.value) {
                            Icons.Filled.ArrowDropUp
                        } else {
                            Icons.Filled.ArrowDropDown
                        },
                        contentDescription = null,
                        tint = UiKitColors.colors.col5
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
            }
        }
        DropdownMenu(
            expanded = showMenu.value,
            onDismissRequest = { showMenu.value = false },
            modifier = Modifier
        ) {

            info.list.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onNameChanged(item)
                        showMenu.value = false
                    },
                    modifier = Modifier
                        .background(UiKitColors.colors.light40)
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(UiKitColors.colors.col3),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${item.displayName}",
                            style = UiKitStyles.H2,
                            color = UiKitColors.colors.light40,
                            modifier = Modifier
                                .fillMaxSize(),
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = Currency.getInstance(item.iso4217).displayName,
                        style = UiKitStyles.Body2,
                        color = UiKitColors.colors.dark,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}