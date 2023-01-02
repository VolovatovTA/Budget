package ru.bysoft.budget.common.token

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.bysoft.budget.common.token.entity.AuthSuccessResponse
import ru.bysoft.budget.common.util.restore
import ru.bysoft.budget.common.util.toJson
import javax.inject.Inject

interface ITokenRepo {
    fun saveTokens(tokenData: AuthSuccessResponse)
    fun getTokens(): AuthSuccessResponse?
    fun clearTokens()
}

class TokenRepo @Inject constructor(
    @ApplicationContext private val context: Context
) : ITokenRepo {
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