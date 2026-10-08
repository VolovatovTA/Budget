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
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import ru.bysoft.android.budget.auth.R

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(buildSignInRequest()).await()
        } catch(e: Exception) {
            e.printStackTrace()
            null
        }
        return result?.pendingIntent?.intentSender
    }

    fun signInWithIntent(intent: Intent?): SignInResult {
        return try {
            SignInResult(
                token = oneTapClient.getSignInCredentialFromIntent(intent).googleIdToken,
                errorMessage = null
            )
        } catch (e: Exception) {
            SignInResult(
                token = null,
                errorMessage = e.message
            )
        }
    }

    // TODO: Разавторизовать пользователя при выходе из профиля
    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
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
