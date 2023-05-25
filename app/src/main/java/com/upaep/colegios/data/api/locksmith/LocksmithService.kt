package com.upaep.colegios.data.api.locksmith

import android.util.Log
import com.google.gson.Gson
import com.upaep.colegios.BuildConfig
import com.upaep.colegios.data.base.ColegiosInterface
import com.upaep.colegios.data.base.encryption.AESHelper
import com.upaep.colegios.data.base.jwt.JwtHelper
import com.upaep.colegios.data.base.retrofit.MyServiceInterceptor
import com.upaep.colegios.data.entities.AnswerBack
import com.upaep.colegios.data.entities.aes.AESAncestral
import com.upaep.colegios.data.entities.locksmith.Locksmith
import com.upaep.colegios.data.entities.locksmith.LocksmithStructure
import com.upaep.colegios.data.entities.upaepservices.UpaepStandardRequest
import com.upaep.colegios.data.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class LocksmithService @Inject constructor(
    private val myServiceInterceptor: MyServiceInterceptor,
    private val api: ColegiosInterface
) {
    suspend fun getLocksmith() : AnswerBack<Locksmith> {
        return withContext(Dispatchers.IO) {
            try {
                myServiceInterceptor.setAuthorization(
                    JwtHelper().createJwt(
                        API_KEY = BuildConfig.LOCKSMITH_AES_KEY,
                        JWT_KEY = BuildConfig.LOCKSMITH_JWT_KEY,
                        JSON_KEY = "apiClient"
                    )
                )
                val gson = Gson()
                val jsonString =
                    gson.toJson(LocksmithStructure(BuildConfig.LOCKSMITH_CONFIG_PROJECT))
                val jsonEncrypt = AESHelper.encrypt(jsonString, AESAncestral)
                val request = UpaepStandardRequest(jsonEncrypt)
                val response = api.getLocksmith(
                    request,
                    url = BuildConfig.LOCKSMITH_URL
                )
                val body = response.body() ?: UpaepStandardResponse()
                Log.i("body", body.toString())
                when(body.statusCode) {
                    200 -> {
                        val decryptdata = AESHelper.decrypt(body.CRYPTDATA, AESAncestral)
                        val upaepResponse = gson.fromJson(decryptdata, Locksmith::class.java)
                        AnswerBack.Success(data = upaepResponse)
                    }
                    else -> {
                        AnswerBack.Error(errorMessage = "")
                    }
                }
            } catch (e: IOException) {
                Log.i("locksmithService", e.toString())
                AnswerBack.NetworkError(errorMessage = "")
            }
        }
    }
}