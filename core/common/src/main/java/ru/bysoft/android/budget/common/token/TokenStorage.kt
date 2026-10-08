package ru.bysoft.android.budget.common.token

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.bysoft.android.budget.common.token.entity.AuthSuccessResponse
import ru.bysoft.android.budget.common.util.restore
import ru.bysoft.android.budget.common.util.toJson

interface ITokenStorage {
    suspend fun saveTokens(tokenData: AuthSuccessResponse)
    fun getTokens(): Flow<AuthSuccessResponse?>
    suspend fun clearTokens()
}

class TokenStorage(
    private val context: Context
) : ITokenStorage {
    companion object {
        private val Context.tokenStorage: DataStore<Preferences> by preferencesDataStore("tokenStore")
        val tokensKey = stringPreferencesKey("field1")
    }

    override suspend fun saveTokens(tokenData: AuthSuccessResponse) {
        context.tokenStorage.edit {
            it[tokensKey] = tokenData.toJson()
        }
    }

    override fun getTokens(): Flow<AuthSuccessResponse?> =
        context.tokenStorage.data.map { prefs ->
            prefs[tokensKey]?.restore<AuthSuccessResponse>()
        }

    override suspend fun clearTokens() {
        context.tokenStorage.edit {
            it.clear()
        }
    }

}