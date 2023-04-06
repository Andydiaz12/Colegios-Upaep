package com.upaep.colegios.data.repository.login

import com.upaep.colegios.data.api.login.LoginService
import com.upaep.colegios.data.base.room.logindao.LoginDao
import com.upaep.colegios.data.entities.login.UserData
import com.upaep.colegios.data.entities.upaepservices.UpaepStandardResponse
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

    suspend fun createSession(userData: UserData) {
        loginDao.createSession(userData)
    }

    suspend fun deleteSession() {
        loginDao.deleteSession()
    }

    suspend fun headerTestCase() {
        return loginService.headerTest()
    }
}