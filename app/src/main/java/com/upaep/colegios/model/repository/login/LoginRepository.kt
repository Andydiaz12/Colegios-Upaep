package com.upaep.colegios.model.repository.login

import com.upaep.colegios.model.api.login.LoginService
import com.upaep.colegios.model.database.logindao.LoginDao
import com.upaep.colegios.model.entities.upaepservices.UpaepStandardResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val loginDao: LoginDao,
    private val loginService: LoginService
) {
    suspend fun doLogin() : UpaepStandardResponse {
        return loginService.doLogin()
    }
}