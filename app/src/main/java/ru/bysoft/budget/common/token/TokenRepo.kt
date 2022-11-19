package ru.bysoft.budget.common.token

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.bysoft.budget.auth.data.network.entity.AuthResponse
import ru.bysoft.budget.common.util.restore
import ru.bysoft.budget.common.util.toJson
import javax.inject.Inject


interface ITokenRepo {
    fun saveToken(tokenData: AuthResponse)
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
}

class TokenRepo @Inject constructor(
    @ApplicationContext private val context: Context
) : ITokenRepo {
    private val tableName = "tokenTableName"
    private val tokenKey = "tokenKey"
    private val shredPrefs = context.getSharedPreferences(tableName, Context.MODE_PRIVATE)

    override fun saveToken(tokenData: AuthResponse) {
//        shredPrefs.edit().putString(tokenKey, tokenData.toJson()).apply()
    }

    override fun getAccessToken() =
        shredPrefs.getString(tokenKey, "")?.restore<AuthResponse>()?.accessToken

    override fun getRefreshToken() =
        shredPrefs.getString(tokenKey, "")?.restore<AuthResponse>()?.refreshToken

}