package ru.bysoft.android.budget.features.settings.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import ru.bysoft.android.budget.common.me_info.entity.MeData
import ru.bysoft.android.budget.common.me_info.entity.SettingsData
import ru.bysoft.android.budget.common.util.getCurrency
import ru.bysoft.android.budget.features.settings.presentation.SettingsViewModel
import ru.bysoft.android.budget.features.settings.presentation.entity.SettingsState
import ru.bysoft.android.budget.uikit.components.buttons.UiKitButton
import ru.bysoft.android.budget.uikit.components.buttons.entity.ButtonType
import ru.bysoft.android.budget.uikit.components.buttons.entity.UiKitButtonInfo
import ru.bysoft.android.budget.uikit.icons.pack.Person
import ru.bysoft.android.budget.uikit.styles.UiKitStyles
import ru.bysoft.android.settings.R

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val state by viewModel.state.collectAsState()
    SettingsScreenContent(state, viewModel::onLogoutClick)
}

@Composable
private fun SettingsScreenContent(state: SettingsState, onLogoutClick: () -> Unit) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var isLoadingSuccess by remember { mutableStateOf(true) }
            val modifier = Modifier
                .padding(30.dp)
                .size(100.dp)
                    // не работает почему-то
                .clip(CircleShape)
            if (isLoadingSuccess) {
                SubcomposeAsyncImage(
                    model = "https://lh3.googleusercontent.com/a/AGNmyxZMBP_uwqLUiYKRXkMjuHbInB5LeicqefblyJwdfg=s96-c",
                    modifier = modifier,
                    loading = {
                        CircularProgressIndicator()
                    },
                    onError = {
                        isLoadingSuccess = false
                    },
                    contentDescription = stringResource(R.string.icon_description),
                    contentScale = androidx.compose.ui.layout.ContentScale.FillBounds
                )
            } else {
                Icon(
                    imageVector = Person,
                    contentDescription = null,
                    modifier = modifier
                )
            }

            DataElement(R.string.your_email, state.meInfoData?.email)
            DataElement(R.string.your_name, state.meInfoData?.name)
            DataElement(
                R.string.your_currency,
                getCurrency(state.meInfoData?.settingsData?.currency)?.displayName
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 30.dp)
            ) {
                UiKitButton(
                    info = UiKitButtonInfo(
                        text = stringResource(R.string.logout),
                        type = ButtonType.MEDIUM
                    ),
                    onClick = onLogoutClick,
                )
            }
        }
    }
}

@Composable
private fun DataElement(key: Int, value: String?) {
    value?.let {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 5.dp)
        ) {
            Text(
                text = stringResource(id = key),
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                style = UiKitStyles.Caption
            )
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = value,
                    modifier = Modifier
                        .padding(start = 10.dp),
                    style = UiKitStyles.Body2
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSettingsScreen() {
    SettingsScreenContent(
        SettingsState(
            MeData(
                "email",
                "name",
                "https://lh3.googleusercontent.com/a/AGNmyxZMBP_uwqLUiYKRXkMjuHbInB5LeicqefblyJwdfg=s96-c",
                SettingsData("RUB"),
                "",
            )
        )
    ) {}
}