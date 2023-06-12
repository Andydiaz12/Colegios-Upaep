package com.upaep.colegios.model.domain.splash

import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.model.entities.locksmith.Locksmith
import com.upaep.colegios.model.repository.locksmith.LocksmithRepository
import javax.inject.Inject

class GetLockSmithUseCase @Inject constructor(private val locksmithRepository: LocksmithRepository) {
    suspend operator fun invoke() : AnswerBack<Locksmith> {
        return locksmithRepository.getLockSmith()
    }
}