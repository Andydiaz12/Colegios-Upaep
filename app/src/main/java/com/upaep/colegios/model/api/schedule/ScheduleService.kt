package com.upaep.colegios.model.api.schedule

import android.util.Log
import com.google.gson.Gson
import com.upaep.colegios.model.base.ColegiosInterface
import com.upaep.colegios.model.base.encryption.AESHelper
import com.upaep.colegios.model.base.encryption.Base64Helper
import com.upaep.colegios.model.base.retrofit.MyServiceInterceptor
import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.model.entities.base.StudentPerseqPersclv
import com.upaep.colegios.model.entities.locksmith.IDDKeychain
import com.upaep.colegios.model.entities.schedule.DayClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class ScheduleService @Inject constructor(
    private val myServiceInterceptor: MyServiceInterceptor,
    private val api: ColegiosInterface
) {
    suspend fun getSchedule(perseq: String, persclv: String, keyChain: IDDKeychain): AnswerBack<List<DayClass>> {
        myServiceInterceptor.setAuthorization(keyChain = keyChain)
        val gson = Gson()
//        val studentData = gson.toJson(StudentPerseqPersclv(persclv = persclv, perseq = perseq))
        val studentData = gson.toJson(StudentPerseqPersclv(persclv = "3382309", perseq = "2"))
        val cryptdata = AESHelper.encrypt(studentData, keychain = keyChain.AESKeychain!!)
        val base64Data = Base64Helper.getBase64(cryptdata)
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getSchedule(cryptData = base64Data)
                when (response.body()?.statusCode) {
                    200 -> {
                        val responseCryptdata = response.body()!!.CRYPTDATA
                        val decryptdata = AESHelper.decrypt(responseCryptdata, keyChain.AESKeychain!!)
                        val schedule: List<DayClass> =
                            gson.fromJson(decryptdata, Array<DayClass>::class.java).asList()
                        Log.i("schedule", schedule.toString())
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