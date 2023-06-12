package com.upaep.colegios.model.domain.base

import com.upaep.colegios.model.entities.login.UserData
import com.upaep.colegios.model.repository.session.SessionRepository
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(private val sessionRepository: SessionRepository) {
    suspend operator fun invoke() : List<UserData> {
        return sessionRepository.getSession()
    }
}