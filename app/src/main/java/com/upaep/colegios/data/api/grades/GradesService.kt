package com.upaep.colegios.data.api.grades

import android.util.Log
import com.google.gson.Gson
import com.upaep.colegios.data.base.ColegiosInterface
import com.upaep.colegios.data.base.encryption.AESHelper
import com.upaep.colegios.data.base.jwt.JwtHelper
import com.upaep.colegios.data.base.retrofit.MyServiceInterceptor
import com.upaep.colegios.data.entities.AnswerBack
import com.upaep.colegios.data.entities.aes.AESKeychain
import com.upaep.colegios.data.entities.base.StudentPerseqPersclv
import com.upaep.colegios.data.entities.grades.GeneralGrades
import com.upaep.colegios.data.entities.upaepservices.UpaepStandardRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class GradesService @Inject constructor(
    private val myServiceInterceptor: MyServiceInterceptor,
    private val api: ColegiosInterface
) {
    suspend fun getGrades(): AnswerBack<List<GeneralGrades>> {
        val studentGrade = StudentPerseqPersclv(persclv = "3188", perseq = "9")
        myServiceInterceptor.setAuthorization(
            authorization = JwtHelper().createJwt(
                API_KEY = "VkKBzVKMw5JdIMBuuPwWYSjNQHOPvTOd",
                JWT_KEY = "vHNI551IlnIi9K8gJKmrU7ENPCtjYOW9"
            )
        )
        return withContext(Dispatchers.IO) {
            try {
                val gson = Gson()
                var cryptdata = gson.toJson(studentGrade)
                val cryptedData = AESHelper.encrypt(
                    cryptdata, aesKeychain = AESKeychain(
                        key = "MgQgB3ibSEsakXDbQtI0ImywMbplkufE",
                        iv = "nIje6EMNsLgQjSfs",
                        blockSize = 256,
                        inputKey = null
                    )
                )
                val response = api.getGrades(cryptData = UpaepStandardRequest(CRYPTDATA = cryptedData))
                if (response.isSuccessful) {
                    when (response.body()?.statusCode) {
                        200 -> {
                            cryptdata = response.body()?.CRYPTDATA
                            val decryptedData = AESHelper.decrypt(
                                cryptdata, aesKeychain = AESKeychain(
                                    key = "MgQgB3ibSEsakXDbQtI0ImywMbplkufE",
                                    iv = "nIje6EMNsLgQjSfs",
                                    blockSize = 256,
                                    inputKey = null
                                )
                            )
                            Log.i("dataGrades", decryptedData)
                        }
                    }
                }
                AnswerBack.Success(data = emptyList())
//                AnswerBack.Success()
            } catch (e: IOException) {
                AnswerBack.NetworkError(errorMessage = "")
            }
        }
    }
}