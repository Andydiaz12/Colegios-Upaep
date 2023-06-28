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

    suspend fun getLockSmithFromService() : Boolean {
        return when(val locksmith = locksmithService.getLocksmith()) {
            is AnswerBack.Success -> {
                deleteLocksmith()
                addLocksmith(locksmith = locksmith.data)
                true
            }
            else -> { false }
        }
    }

    suspend fun getLockSmith() : Locksmith {
        return locksmithDao.getLocksmith()
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