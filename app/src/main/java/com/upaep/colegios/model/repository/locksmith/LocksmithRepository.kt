package com.upaep.colegios.model.repository.locksmith

import com.upaep.colegios.model.api.locksmith.LocksmithService
import com.upaep.colegios.model.database.locksmithdao.LocksmithDao
import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.model.entities.locksmith.Locksmith
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocksmithRepository @Inject constructor(
    private val locksmithService: LocksmithService,
    private val locksmithDao: LocksmithDao
) {
    suspend fun getLockSmith() : AnswerBack<Locksmith> {
        val locksmith = locksmithService.getLocksmith()
        when(locksmith) {
            is AnswerBack.Success -> {
                deleteLocksmith()
                addLocksmith(locksmith = locksmith.data)
            }
            is AnswerBack.AccessDenied -> {}
            is AnswerBack.Error -> {}
            is AnswerBack.NetworkError -> {}
            is AnswerBack.InternalError -> {}
        }
        return locksmith
    }

    suspend fun getLocksmithFromDB(): Locksmith {
        return locksmithDao.getLocksmith()
    }

    suspend fun addLocksmith(locksmith: Locksmith) {
        locksmithDao.addLocksmith(locksmith = locksmith)
    }

    suspend fun deleteLocksmith() {
        locksmithDao.deleteLocksmith()
    }
}