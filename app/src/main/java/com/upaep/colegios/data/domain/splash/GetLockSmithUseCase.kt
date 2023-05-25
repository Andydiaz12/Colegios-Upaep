package com.upaep.colegios.data.domain.splash

import com.upaep.colegios.data.entities.AnswerBack
import com.upaep.colegios.data.entities.locksmith.Locksmith
import com.upaep.colegios.data.repository.locksmith.LocksmithRepository
import javax.inject.Inject

class GetLockSmithUseCase @Inject constructor(private val locksmithRepository: LocksmithRepository) {
    suspend operator fun invoke() : AnswerBack<Locksmith> {
        return locksmithRepository.getLockSmith()
    }
}