package com.upaep.colegios.model.repository.invoice

import android.content.Context
import android.security.KeyChain
import android.util.Log
import com.upaep.colegios.model.api.invoice.InvoiceService
import com.upaep.colegios.model.database.invoicedao.InvoiceDao
import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.model.entities.locksmith.IDDKeychain
import com.upaep.colegios.model.entities.payments.PaymentDescription
import com.upaep.colegios.model.entities.studentselector.StudentsSelector
import javax.inject.Inject

class InvoiceRepository @Inject constructor(
    private val invoiceService: InvoiceService,
    private val invoiceDao: InvoiceDao
) {
    suspend fun getInvoice(
        keyChain: IDDKeychain,
        student: StudentsSelector
    ): AnswerBack<List<PaymentDescription>> {
        val invoice = invoiceService.getInvoice(keyChain = keyChain, student = student)
        when (invoice) {
            is AnswerBack.Success -> {
                invoiceDao.deleteInvoice()
                invoiceDao.addInvoice(paymentDescription = invoice.data)
                invoiceDao.getInvoice()
            }
            is AnswerBack.AccessDenied -> TODO()
            is AnswerBack.Error -> TODO()
            is AnswerBack.InternalError -> TODO()
            is AnswerBack.NetworkError -> TODO()
        }
        return invoice
    }
}