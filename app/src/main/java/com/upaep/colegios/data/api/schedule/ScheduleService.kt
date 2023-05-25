package com.upaep.colegios.data.api.schedule

import android.util.Log
import com.google.gson.Gson
import com.upaep.colegios.data.base.ColegiosInterface
import com.upaep.colegios.data.base.encryption.AESHelper
import com.upaep.colegios.data.base.encryption.Base64Helper
import com.upaep.colegios.data.base.jwt.JwtHelper
import com.upaep.colegios.data.base.retrofit.MyServiceInterceptor
import com.upaep.colegios.data.entities.AnswerBack
import com.upaep.colegios.data.entities.aes.AESKeychain
import com.upaep.colegios.data.entities.base.StudentPerseqPersclv
import com.upaep.colegios.data.entities.schedule.DayClass
import com.upaep.colegios.data.entities.upaepservices.UpaepStandardRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class ScheduleService @Inject constructor(
    private val myServiceInterceptor: MyServiceInterceptor,
    private val api: ColegiosInterface
) {
    suspend fun getSchedule(perseq: String, persclv: String): AnswerBack<List<DayClass>> {
        myServiceInterceptor.setAuthorization(
            authorization = JwtHelper().createJwt(
                API_KEY = "VkKBzVKMw5JdIMBuuPwWYSjNQHOPvTOd",
                JWT_KEY = "vHNI551IlnIi9K8gJKmrU7ENPCtjYOW9"
            )
        )
        val gson = Gson()
        val studentData = gson.toJson(StudentPerseqPersclv(persclv = persclv, perseq = perseq))
        val aesKeychain = AESKeychain(
            key = "MgQgB3ibSEsakXDbQtI0ImywMbplkufE",
            iv = "nIje6EMNsLgQjSfs",
            blockSize = 256,
            inputKey = null
        )
        val cryptdata = AESHelper.encrypt(studentData, aesKeychain = aesKeychain)
        val base64Data = Base64Helper.getBase64(cryptdata)
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getSchedule(cryptData = base64Data)
                when (response.body()?.statusCode) {
                    200 -> {
                        val responseCryptdata = response.body()!!.CRYPTDATA
                        val decryptdata = AESHelper.decrypt(responseCryptdata, aesKeychain)
                        val schedule: List<DayClass> =
                            gson.fromJson(decryptdata, Array<DayClass>::class.java).asList()
                        AnswerBack.Success(data = schedule)
                    }

                    else -> {
                        AnswerBack.Error(errorMessage = "")
                    }
                }
            } catch (e: IOException) {
                AnswerBack.NetworkError()
            }
        }
    }
}