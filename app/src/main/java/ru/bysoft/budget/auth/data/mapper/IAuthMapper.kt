package ru.bysoft.budget.auth.data.mapper

import javax.inject.Inject

interface IAuthMapper {
    fun getCodeResponse()
}

class AuthMapper @Inject constructor() : IAuthMapper {
    override fun getCodeResponse() {
        TODO("Not yet implemented")
    }

}