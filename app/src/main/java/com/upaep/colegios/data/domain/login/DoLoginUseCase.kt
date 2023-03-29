package com.upaep.colegios.data.domain.login

import com.upaep.colegios.data.entities.upaepservices.UpaepStandardResponse
import com.upaep.colegios.data.repository.login.LoginRepository
import javax.inject.Inject

class DoLoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(): UpaepStandardResponse {
        return loginRepository.doLogin()
    }
}