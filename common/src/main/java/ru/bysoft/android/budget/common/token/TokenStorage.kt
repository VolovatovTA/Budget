package ru.bysoft.android.budget.common.token

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.bysoft.android.budget.common.token.entity.AuthSuccessResponse
import ru.bysoft.android.budget.common.util.restore
import ru.bysoft.android.budget.common.util.toJson
import javax.inject.Inject

interface ITokenStorage {
    fun saveTokens(tokenData: AuthSuccessResponse)
    fun getTokens(): AuthSuccessResponse?
    fun clearTokens()
}

class TokenStorage @Inject constructor(
    @ApplicationContext private val context: Context
) : ITokenStorage {
    private val tableName = "tokenTableName"
    private val tokenKey = "tokenKey"
    private val shredPrefs = context.getSharedPreferences(tableName, Context.MODE_PRIVATE)

    override fun saveTokens(tokenData: AuthSuccessResponse) {
        shredPrefs.edit().putString(tokenKey, tokenData.toJson()).apply()
    }

    override fun getTokens() =
        shredPrefs.getString(tokenKey, "")?.restore<AuthSuccessResponse>()

    override fun clearTokens() {
        shredPrefs.edit().clear().commit()
    }

}