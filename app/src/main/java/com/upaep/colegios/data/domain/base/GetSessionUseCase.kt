package com.upaep.colegios.data.domain.base

import com.upaep.colegios.data.entities.login.UserData
import com.upaep.colegios.data.repository.session.SessionRepository
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(private val sessionRepository: SessionRepository) {
    suspend operator fun invoke() : List<UserData> {
        return sessionRepository.getSession()
    }
}