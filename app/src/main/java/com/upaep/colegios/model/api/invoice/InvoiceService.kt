package com.upaep.colegios.model.api.invoice

import android.util.Log
import com.google.gson.Gson
import com.upaep.colegios.model.base.ColegiosInterface
import com.upaep.colegios.model.base.encryption.AESHelper
import com.upaep.colegios.model.base.encryption.Base64Helper
import com.upaep.colegios.model.base.retrofit.MyServiceInterceptor
import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.model.entities.base.StudentPerseqPersclv
import com.upaep.colegios.model.entities.locksmith.IDDKeychain
import com.upaep.colegios.model.entities.payments.PaymentDescription
import com.upaep.colegios.model.entities.studentselector.StudentsSelector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class InvoiceService @Inject constructor(
    private val myServiceInterceptor: MyServiceInterceptor,
    private val api: ColegiosInterface
) {
    suspend fun getInvoice(keyChain: IDDKeychain, student: StudentsSelector) : AnswerBack<List<PaymentDescription>> {
        myServiceInterceptor.setAuthorization(keyChain)
        val gson = Gson()
//        val studentString = gson.toJson(StudentPerseqPersclv(persclv = student.persclv, perseq = student.perseq))
        val studentString = gson.toJson(StudentPerseqPersclv(persclv = "5689", perseq = "10"))
        val cryptedData = AESHelper.encrypt(studentString, keychain = keyChain.AESKeychain!!)
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getInvoice(cryptData = Base64Helper.getBase64(cryptedData))
                if(response.isSuccessful) {
                    when(response.body()?.statusCode) {
                        200 -> {
                            val responseCryptdata = response.body()!!.CRYPTDATA
                            val decryptdata = AESHelper.decrypt(responseCryptdata, keychain = keyChain.AESKeychain!!)
                            val invoice = gson.fromJson(decryptdata, Array<PaymentDescription>::class.java).asList()
                            AnswerBack.Success(data = invoice)
                        }
                        500 -> {
                            AnswerBack.InternalError(errorMessage = "")
                        }
                        else -> {
                            AnswerBack.Error(errorMessage = "")
                        }
                    }
                } else {
                    AnswerBack.InternalError(errorMessage = "No nos pudimos conectar a nuestros servidores, intentelo más tarde")
                }
            } catch (e: IOException) {
                AnswerBack.InternalError(errorMessage = "")
            }
        }
    }
}