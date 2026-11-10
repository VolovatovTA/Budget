// Google One Tap (SignInClient) is deprecated in favour of Credential Manager. The migration
// changes the sign-in flow and is a separate task; until then the warnings are silenced here.
@file:Suppress("DEPRECATION")

package ru.bysoft.android.budget.auth.presentation

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import kotlinx.coroutines.tasks.await
import ru.bysoft.android.budget.auth.R
import ru.bysoft.android.budget.common.util.runCatchingCancellable

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {

    suspend fun signIn(): IntentSender? {
        val result = runCatchingCancellable {
            oneTapClient.beginSignIn(buildSignInRequest()).await()
        }.onFailure { it.printStackTrace() }.getOrNull()
        return result?.pendingIntent?.intentSender
    }

    fun signInWithIntent(intent: Intent?): SignInResult {
        return runCatchingCancellable {
            oneTapClient.getSignInCredentialFromIntent(intent).googleIdToken
        }.fold(
            onSuccess = { SignInResult(token = it, errorMessage = null) },
            onFailure = { SignInResult(token = null, errorMessage = it.message) },
        )
    }

    // TODO(#39): sign the user out of Google as well when leaving the profile
    suspend fun signOut() {
        runCatchingCancellable { oneTapClient.signOut().await() }
            .onFailure { it.printStackTrace() }
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}

data class SignInResult(
    val token: String?,
    val errorMessage: String?
)
