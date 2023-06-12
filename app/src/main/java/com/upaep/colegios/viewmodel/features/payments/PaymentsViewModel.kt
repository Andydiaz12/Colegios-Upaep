package com.upaep.colegios.viewmodel.features.payments

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upaep.colegios.model.entities.payments.PaymentDescription
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(application: Application) : ViewModel() {

    private val context = application.applicationContext
    private val paymentFormat = NumberFormat.getCurrencyInstance(Locale.US)
    private val _lastCheckedElement = MutableLiveData(0)
    private val _paymentList = MutableLiveData(getPartials())
    private val _totalPaymentSum = MutableLiveData("$0.00")
    var lastCheckedElement: LiveData<Int> = _lastCheckedElement
    val paymentList: LiveData<List<PaymentDescription>> = _paymentList
    val totalPaymentSum: LiveData<String> = _totalPaymentSum

    fun updateLastCheckedElement(index: Int, partial: PaymentDescription) {
        Log.i("blockedValidation-1", partial.toString())
        if (!partial.checked) {
            val updatedList = _paymentList.value!!.toMutableList()
            updatedList[index].checked = true
            Log.i("updatedList", updatedList.toString())
            _paymentList.value.let {

            }
        } else {
            val lastChecked = _paymentList.value!!.indexOfFirst { element -> !element.checked } - 1
            _lastCheckedElement.value = lastChecked
        }
        Log.i("blockedValidation-2", partial.toString())
    }

    fun adjustTotalSum(amount: String, checked: Boolean) {
        val currentSum = castToDouble(_totalPaymentSum.value!!)
        val operationValue = castToDouble(amount) * (if(checked) 1 else - 1)
        _totalPaymentSum.value = paymentFormat.format( currentSum + operationValue)
    }

    private fun castToDouble(value: String) : Double = value.replace(oldValue = "$", newValue = "").replace(oldValue = ",", newValue = "").toDouble()

    fun getPartials(): List<PaymentDescription> {
        return listOf(
            PaymentDescription(
                name = "Colegiatura abril",
                description = "Venció el 20/06/2023",
                amount = "\$2,240.00"
            ),
            PaymentDescription(
                name = "Colegiatura marzo",
                description = "Venció el 20/06/2023",
                amount = "\$2,300.00"
            ),
            PaymentDescription(
                name = "Colegiatura febrero",
                description = "Venció el 20/06/2023",
                amount = "\$2,200.00"
            ),
            PaymentDescription(
                name = "Colegiatura marzo",
                description = "Venció el 20/06/2023",
                amount = "\$2,300.00"
            ),
            PaymentDescription(
                name = "Colegiatura marzo",
                description = "Venció el 20/06/2023",
                amount = "\$2,300.00"
            ),
            PaymentDescription(
                name = "Colegiatura marzo",
                description = "Venció el 20/06/2023",
                amount = "\$2,300.00"
            ),
            PaymentDescription(
                name = "Colegiatura marzo",
                description = "Venció el 20/06/2023",
                amount = "\$2,300.00"
            ),
            PaymentDescription(
                name = "Colegiatura marzo",
                description = "Venció el 20/06/2023",
                amount = "\$2,300.00"
            ),
            PaymentDescription(
                name = "Colegiatura marzo",
                description = "Venció el 20/06/2023",
                amount = "\$2,300.00"
            ),
            PaymentDescription(
                name = "Colegiatura marzo",
                description = "Venció el 20/06/2023",
                amount = "\$2,300.00"
            ),
            PaymentDescription(
                name = "Colegiatura marzo",
                description = "Venció el 20/06/2023",
                amount = "\$2,300.00"
            ),
            PaymentDescription(
                name = "Colegiatura marzo",
                description = "Venció el 20/06/2023",
                amount = "\$2,300.00"
            ),
            PaymentDescription(
                name = "Colegiatura marzo",
                description = "Venció el 20/06/2023",
                amount = "\$2,300.00"
            )
        )
    }
}