package ru.bysoft.budget.common.token

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.bysoft.budget.common.token.entity.AuthSuccessResponse
import ru.bysoft.budget.common.util.restore
import ru.bysoft.budget.common.util.toJson
import javax.inject.Inject


interface ITokenRepo {
    fun saveTokens(tokenData: AuthSuccessResponse)
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
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

    override fun getAccessToken() =
        shredPrefs.getString(tokenKey, "")?.restore<AuthSuccessResponse>()?.accessToken

    override fun getRefreshToken() =
        shredPrefs.getString(tokenKey, "")?.restore<AuthSuccessResponse>()?.refreshToken

    override fun clearTokens() {
        shredPrefs.edit().clear().apply()
    }

}