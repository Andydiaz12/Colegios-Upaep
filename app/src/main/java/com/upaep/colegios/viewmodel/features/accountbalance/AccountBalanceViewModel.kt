package com.upaep.colegios.viewmodel.features.accountbalance

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.colegios.model.base.UserPreferences
import com.upaep.colegios.model.domain.accountbalance.GetAccountBalanceUseCase
import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.model.entities.accountbalance.AccountBalance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AccountBalanceViewModel @Inject constructor(
    application: Application,
    getAccountBalanceUseCase: GetAccountBalanceUseCase
) :
    ViewModel() {
    private val _listPayments = MutableLiveData<List<AccountBalance>>(emptyList())
    val listPayments: LiveData<List<AccountBalance>> = _listPayments
    private val userPreferences = UserPreferences(application.applicationContext)

    init {
        viewModelScope.launch {
            userPreferences.getSelectedStudent.collect { student ->
                val accountData = when (val accountData =
                    getAccountBalanceUseCase(perseq = student.perseq, persclv = student.perseq)) {
                    is AnswerBack.Error -> {
                        accountData.data
                        //Display msg
                    }

                    is AnswerBack.Success -> {
                        accountData.data
                    }

                    else -> {
                        emptyList()
                    }
                }
                val format = NumberFormat.getCurrencyInstance(Locale.US)
                format.minimumFractionDigits = 2
                for (data in accountData ?: emptyList()) { data.edrsPago = format.format(data.edrsPago.toFloat()) }
                _listPayments.value = accountData
            }
        }
    }
}