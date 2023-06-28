package com.upaep.colegios.model.api.locksmith

import android.util.Log
import com.google.gson.Gson
import com.upaep.colegios.BuildConfig
import com.upaep.colegios.model.base.ColegiosInterface
import com.upaep.colegios.model.base.encryption.AESHelper
import com.upaep.colegios.model.base.jwt.JwtHelper
import com.upaep.colegios.model.base.retrofit.MyServiceInterceptor
import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.model.entities.aes.AESAncestral
import com.upaep.colegios.model.entities.locksmith.IDDKeychain
import com.upaep.colegios.model.entities.locksmith.JWTKeychain
import com.upaep.colegios.model.entities.locksmith.Locksmith
import com.upaep.colegios.model.entities.locksmith.LocksmithStructure
import com.upaep.colegios.model.entities.upaepservices.UpaepStandardRequest
import com.upaep.colegios.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class LocksmithService @Inject constructor(
    private val myServiceInterceptor: MyServiceInterceptor,
    private val api: ColegiosInterface
) {
    suspend fun getLocksmith(): AnswerBack<Locksmith> {
        return withContext(Dispatchers.IO) {
            try {
                myServiceInterceptor.setAuthorization(
                    IDDKeychain(
                        JWTKeychain = JWTKeychain(
                            apiKey = BuildConfig.LOCKSMITH_AES_KEY,
                            jwtKey = BuildConfig.LOCKSMITH_JWT_KEY,
                            jsonKey = "apiClient"
                        )
                    )
                )
                val gson = Gson()
                val jsonString =
                    gson.toJson(LocksmithStructure(BuildConfig.LOCKSMITH_CONFIG_PROJECT))
                val jsonEncrypt = AESHelper.encrypt(jsonString, AESAncestral.getAES())
                val request = UpaepStandardRequest(jsonEncrypt)
                val response = api.getLocksmith(
                    request,
                    url = BuildConfig.LOCKSMITH_URL
                )
                val body = response.body() ?: UpaepStandardResponse()
                Log.i("locksmith", body.message)
                Log.i("locksmith", body.error.toString())
                Log.i("locksmith", body.statusCode.toString())
                when (body.statusCode) {
                    200 -> {
                        val decryptdata = AESHelper.decrypt(body.CRYPTDATA, AESAncestral.getAES())
                        val upaepResponse = gson.fromJson(decryptdata, Locksmith::class.java)
                        AnswerBack.Success(data = upaepResponse)
                    }

                    else -> {
                        AnswerBack.Error(errorMessage = "")
                    }
                }
            } catch (e: IOException) {
                AnswerBack.NetworkError(errorMessage = "")
            }
        }
    }
}