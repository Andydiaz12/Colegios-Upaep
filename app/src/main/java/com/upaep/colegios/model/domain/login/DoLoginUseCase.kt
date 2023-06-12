package com.upaep.colegios.model.domain.login

import com.upaep.colegios.model.entities.upaepservices.UpaepStandardResponse
import com.upaep.colegios.model.repository.login.LoginRepository
import javax.inject.Inject

class DoLoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(): UpaepStandardResponse {
        return loginRepository.doLogin()
    }
}