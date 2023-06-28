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
    //private val _paymentList = MutableLiveData(getPartials())
    private val _paymentList = mutableStateListOf<PaymentDescription>()
    private val _totalPaymentSum = MutableLiveData("$0.00")
    var lastCheckedElement: LiveData<Int> = _lastCheckedElement
    //val paymentList: LiveData<List<PaymentDescription>> = _paymentList
    val paymentList: List<PaymentDescription> = _paymentList
    val totalPaymentSum: LiveData<String> = _totalPaymentSum

    private val _testVariable = mutableStateListOf<PaymentDescription>()
    val testVariable: List<PaymentDescription> = _testVariable

    /*init {
        _testVariable.addAll(getPartials())
    }

    fun updateLastCheckedElement(paymentDescription: PaymentDescription, amount: String) {
        val elementToFind =
            if (!paymentDescription.checked) paymentDescription else _testVariable.findLast { it.checked }
        val index = _testVariable.indexOf(elementToFind)
        _testVariable[index] = _testVariable[index].let {
            it.copy(checked = !it.checked)
        }
        adjustTotalSum(amount, paymentDescription.checked)
    }

    fun adjustTotalSum(amount: String, checked: Boolean) {
        val currentSum = castToDouble(_totalPaymentSum.value!!)
        val operationValue = castToDouble(amount) * (if(!checked) 1 else -1)
        _totalPaymentSum.value = paymentFormat.format(currentSum + operationValue)
    }

    private fun castToDouble(value: String): Double =
        value.replace(oldValue = "$", newValue = "").replace(oldValue = ",", newValue = "")
            .toDouble()

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
                name = "Colegiatura enero",
                description = "Venció el 20/06/2023",
                amount = "\$2,300.00"
            )
        )
    }*/
}