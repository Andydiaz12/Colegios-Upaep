package com.upaep.colegios.data.repository.session

import com.upaep.colegios.data.database.sessiondao.SessionDao
import com.upaep.colegios.data.entities.login.UserData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository @Inject constructor(private val sessionDao: SessionDao) {
    suspend fun getSession() : List<UserData> {
        return sessionDao.getSession()
    }

    suspend fun deleteSession() {
        sessionDao.deleteSession()
    }

    suspend fun createSession(userData: UserData) {
        sessionDao.createSession(userData)
    }
}