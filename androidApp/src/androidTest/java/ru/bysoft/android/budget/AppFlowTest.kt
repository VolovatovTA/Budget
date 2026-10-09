package ru.bysoft.android.budget

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext
import ru.bysoft.android.budget.common.token.ITokenStorage

/**
 * End-to-end flows on the localMock flavor: the real app with the mock backend from
 * core/network/src/main/assets. Run with
 * `./gradlew :androidApp:connectedLocalMockDebugAndroidTest` on a device or emulator.
 */
@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class AppFlowTest {

    @get:Rule
    val compose = createEmptyComposeRule()

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun startSignedOut() {
        // the app is already created by the test runner, so Koin is up; forget any saved session
        runBlocking { GlobalContext.get().get<ITokenStorage>().clearTokens() }
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun close() = scenario.close()

    @Test
    fun signIn_showsWalletsAndTransactionsFromTheMockBackend() {
        signIn()

        compose.onNodeWithText("Main card").assertExists()
        compose.onNodeWithText("Weekly groceries").assertExists()
    }

    @Test
    fun tapOnTransaction_opensItsDetails() {
        signIn()

        compose.onNodeWithText("Weekly groceries").performClick()

        compose.waitUntilAtLeastOneExists(hasText("86.40", substring = true), LOAD_TIMEOUT)
        compose.onNodeWithText("Expense").assertExists()
        compose.onNodeWithText("Groceries").assertExists()
    }

    private fun signIn() {
        compose.waitUntilAtLeastOneExists(hasText("Email"), SPLASH_TIMEOUT)
        compose.onNode(hasSetTextAction() and hasText("Email")).performTextInput("alex@example.com")
        compose.onNode(hasSetTextAction() and hasText("Password")).performTextInput("password1")
        compose.onNodeWithText("SIGN IN").performClick()
        compose.waitUntilAtLeastOneExists(hasText("Main card"), LOAD_TIMEOUT)
        // transactions are requested after the wallets, with their own mock delay
        compose.waitUntilAtLeastOneExists(hasText("Weekly groceries"), LOAD_TIMEOUT)
    }

    private companion object {
        const val SPLASH_TIMEOUT = 10_000L
        /** mock APIs answer after a 1 s delay each; wallets, me and transactions load in sequence */
        const val LOAD_TIMEOUT = 20_000L
    }
}
