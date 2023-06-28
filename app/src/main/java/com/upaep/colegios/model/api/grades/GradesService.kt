package com.upaep.colegios.model.api.grades

import android.util.Log
import com.upaep.colegios.model.base.ColegiosInterface
import com.upaep.colegios.model.base.retrofit.MyServiceInterceptor
import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.model.entities.grades.GeneralGrades
import javax.inject.Inject

class GradesService @Inject constructor(
    private val myServiceInterceptor: MyServiceInterceptor,
    private val api: ColegiosInterface
) {

//    suspend fun getGrades(): AnswerBack<List<GeneralGrades>> {
//        val studentGrade = StudentPerseqPersclv(persclv = "3188", perseq = "9")
//        myServiceInterceptor.setAuthorization(
//            authorization = JwtHelper().createJwt(
//                API_KEY = "VkKBzVKMw5JdIMBuuPwWYSjNQHOPvTOd",
//                JWT_KEY = "vHNI551IlnIi9K8gJKmrU7ENPCtjYOW9"
//            )
//        )
//        return withContext(Dispatchers.IO) {
//            try {
//                val gson = Gson()
//                var cryptdata = gson.toJson(studentGrade)
//                val cryptedData = AESHelper.encrypt(
//                    cryptdata, aesKeychain = AESKeychain(
//                        key = "MgQgB3ibSEsakXDbQtI0ImywMbplkufE",
//                        iv = "nIje6EMNsLgQjSfs",
//                        blockSize = 256,
//                        inputKey = null
//                    )
//                )
//                val response = api.getGrades(cryptData = Base64Helper.getBase64(cryptedData))
//                Log.i("dataGrades", response.toString())
//                Log.i("dataGrades", response.body().toString())
//                when (response.body()?.statusCode) {
//                    200 -> {
//                        cryptdata = response.body()?.CRYPTDATA
//                        val decryptedData = AESHelper.decrypt(
//                            cryptdata, aesKeychain = AESKeychain(
//                                key = "MgQgB3ibSEsakXDbQtI0ImywMbplkufE",
//                                iv = "nIje6EMNsLgQjSfs",
//                                blockSize = 256,
//                                inputKey = null
//                            )
//                        )
//                        //val grades = gson.fromJson(decryptedData, Array<>::class.java).asList()
//                        Log.i("dataGrades", decryptedData)
//                    }
//                }
//                AnswerBack.Success(data = emptyList())
////                AnswerBack.Success()
//            } catch (e: IOException) {
//                AnswerBack.NetworkError(errorMessage = "")
//            }
//        }
//    }
}