package com.upaep.colegios.data.api.studentselector

import android.util.Log
import com.google.gson.Gson
import com.upaep.colegios.data.base.ColegiosInterface
import com.upaep.colegios.data.base.encryption.AESHelper
import com.upaep.colegios.data.base.jwt.JwtHelper
import com.upaep.colegios.data.base.retrofit.MyServiceInterceptor
import com.upaep.colegios.data.entities.AnswerBack
import com.upaep.colegios.data.entities.aes.AESKeychain
import com.upaep.colegios.data.entities.studentselector.GetStudentModel
import com.upaep.colegios.data.entities.studentselector.StudentsIds
import com.upaep.colegios.data.entities.studentselector.StudentsSelector
import com.upaep.colegios.data.entities.upaepservices.UpaepStandardRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class StudentSelectorService @Inject constructor(
    private val myServiceInterceptor: MyServiceInterceptor,
    private val api: ColegiosInterface
) {

    suspend fun getStudents() : AnswerBack<List<StudentsSelector>> {
        myServiceInterceptor.setAuthorization(
            authorization = JwtHelper().createJwt(
                API_KEY = "VkKBzVKMw5JdIMBuuPwWYSjNQHOPvTOd",
                JWT_KEY = "vHNI551IlnIi9K8gJKmrU7ENPCtjYOW9"
            )
        )

        val student = GetStudentModel(
            IDS = listOf(StudentsIds(id = "5568"), StudentsIds(id = "3392190"))
        )
        val gson = Gson()
        val cryptdata = gson.toJson(student)
        val cryptedData = AESHelper.encrypt(
            cryptdata,
            aesKeychain = AESKeychain(
                key = "MgQgB3ibSEsakXDbQtI0ImywMbplkufE",
                iv = "nIje6EMNsLgQjSfs",
                blockSize = 256,
                inputKey = null
            )
        )
        return withContext(Dispatchers.IO) {
            try {
                val response =
                    api.getStudents(cryptdata = UpaepStandardRequest(CRYPTDATA = cryptedData))
                when (response.body()?.statusCode) {
                    200 -> {
                        val responseCryptdata = response.body()!!.CRYPTDATA
                        val decryptdata = AESHelper.decrypt(
                            responseCryptdata,
                            AESKeychain(
                                iv = "nIje6EMNsLgQjSfs",
                                key = "MgQgB3ibSEsakXDbQtI0ImywMbplkufE",
                                blockSize = 256,
                                inputKey = null
                            )
                        )
                        Log.i("decryptData", decryptdata)
                        val childs = gson.fromJson(decryptdata, Array<StudentsSelector>::class.java).asList()
                        AnswerBack.Success(data = childs)
                    }
                    500 -> {
                        AnswerBack.InternalError(errorMessage = "")
                    }
                    else -> {
                        AnswerBack.Error(errorMessage = "")
                    }
                }
            } catch (e: IOException) {
                AnswerBack.InternalError(errorMessage = "")
            }
        }
    }
}