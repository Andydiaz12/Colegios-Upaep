package com.upaep.colegios.data.api.login

import com.upaep.colegios.data.base.ColegiosInterface
import com.upaep.colegios.data.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class LoginService @Inject constructor(private val api: ColegiosInterface) {

    suspend fun doLogin(): UpaepStandardResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.doLogin()
                response.body() ?: UpaepStandardResponse()
            } catch (e: IOException) {
                //Error de conexión
                UpaepStandardResponse()
            }
        }
    }
}