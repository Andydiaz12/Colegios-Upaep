package com.upaep.colegios.model.domain.splash

import com.upaep.colegios.model.repository.locksmith.LocksmithRepository
import javax.inject.Inject

class GetLockSmithUseCase @Inject constructor(private val locksmithRepository: LocksmithRepository) {
    suspend operator fun invoke() : Boolean {
        return locksmithRepository.getLockSmithFromService()
    }
}