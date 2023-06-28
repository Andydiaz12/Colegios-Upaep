package com.upaep.colegios.model.api.studentselector

import android.util.Log
import com.google.gson.Gson
import com.upaep.colegios.model.base.ColegiosInterface
import com.upaep.colegios.model.base.encryption.AESHelper
import com.upaep.colegios.model.base.retrofit.MyServiceInterceptor
import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.model.entities.locksmith.IDDKeychain
import com.upaep.colegios.model.entities.studentselector.GetStudentModel
import com.upaep.colegios.model.entities.studentselector.StudentsIds
import com.upaep.colegios.model.entities.studentselector.StudentsSelector
import com.upaep.colegios.model.entities.upaepservices.UpaepStandardRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class StudentSelectorService @Inject constructor(
    private val myServiceInterceptor: MyServiceInterceptor,
    private val api: ColegiosInterface
) {

    suspend fun getStudents(keyChain: IDDKeychain) : AnswerBack<List<StudentsSelector>> {
        myServiceInterceptor.setAuthorization(keyChain = keyChain)
        val student = GetStudentModel(
            IDS = listOf(StudentsIds(id = "5568"), StudentsIds(id = "3392190"))
        )
        val gson = Gson()
        val cryptdata = gson.toJson(student)
        val cryptedData = AESHelper.encrypt(cryptdata, keychain = keyChain.AESKeychain!!)
        return withContext(Dispatchers.IO) {
            try {
                val response =
                    api.getStudents(cryptdata = UpaepStandardRequest(CRYPTDATA = cryptedData))
                when (response.body()?.statusCode) {
                    200 -> {
                        val responseCryptdata = response.body()!!.CRYPTDATA
                        val decryptdata = AESHelper.decrypt(responseCryptdata, keychain = keyChain.AESKeychain!!)
                        val children = gson.fromJson(decryptdata, Array<StudentsSelector>::class.java).asList()
                        AnswerBack.Success(data = children)
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