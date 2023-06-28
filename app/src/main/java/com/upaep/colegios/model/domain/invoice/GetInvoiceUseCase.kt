package com.upaep.colegios.model.domain.invoice

import android.content.Context
import android.util.Log
import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.model.entities.payments.PaymentDescription
import com.upaep.colegios.model.entities.studentselector.StudentsSelector
import com.upaep.colegios.model.repository.invoice.InvoiceRepository
import com.upaep.colegios.model.repository.locksmith.LocksmithRepository
import javax.inject.Inject

class GetInvoiceUseCase @Inject constructor(
    private val invoiceRepository: InvoiceRepository,
    private val locksmithRepository: LocksmithRepository
) {
    suspend operator fun invoke(selectedStudent: StudentsSelector) : AnswerBack<List<PaymentDescription>> {
        return invoiceRepository.getInvoice(
            locksmithRepository.getLockSmith().colegiosUpaepAcademico!!,
            student = selectedStudent
        )
    }
}