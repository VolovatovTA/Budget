package ru.bysoft.budget.auth.data

import ru.bysoft.budget.auth.data.mapper.IAuthMapper
import ru.bysoft.budget.auth.data.network.IAuthApi
import javax.inject.Inject

interface IAuthRepository{
    suspend fun signIn(phone: String)
    suspend fun signUp(phone: String)
}

class AuthRepository @Inject constructor(
    private val mapper: IAuthMapper,
    private val api: IAuthApi
): IAuthRepository {

    override suspend fun signIn(phone: String) {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(phone: String) {
        TODO("Not yet implemented")
    }
}