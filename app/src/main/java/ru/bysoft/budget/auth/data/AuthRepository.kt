package ru.bysoft.budget.auth.data

import ru.bysoft.budget.auth.data.mapper.IAuthMapper
import ru.bysoft.budget.auth.network.IAuthApi
import javax.inject.Inject

interface IAuthRepository{
    suspend fun sendUserCredential(phone: String)
}

class AuthRepository @Inject constructor(
    private val mapper: IAuthMapper,
    private val api: IAuthApi
): IAuthRepository {
    override suspend fun sendUserCredential(phone: String) {
        TODO("Not yet implemented")
    }
}