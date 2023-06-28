package com.upaep.colegios.viewmodel.features.invoice

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.colegios.model.base.UserPreferences
import com.upaep.colegios.model.domain.invoice.GetInvoiceUseCase
import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.model.entities.payments.PaymentDescription
import com.upaep.colegios.model.entities.studentselector.StudentsSelector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoiceViewModel @Inject constructor(
    getInvoiceUseCase: GetInvoiceUseCase,
    application: Application
) : ViewModel() {
    private val _invoiceList = MutableLiveData<List<PaymentDescription>>()
    val invoiceList: LiveData<List<PaymentDescription>> = _invoiceList
    init {
        viewModelScope.launch {
            UserPreferences(application.applicationContext).getSelectedStudent.collect { student ->
                when(val invoice = getInvoiceUseCase(selectedStudent = student)) {
                    is AnswerBack.Success -> {
                        _invoiceList.value = invoice.data
                        Log.i("invoiceData", _invoiceList.value.toString())
                    }
                    is AnswerBack.AccessDenied -> {}
                    is AnswerBack.Error -> {}
                    is AnswerBack.InternalError -> {}
                    is AnswerBack.NetworkError -> {}
                }
            }
        }
    }
}