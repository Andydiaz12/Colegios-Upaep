package com.upaep.colegios.viewmodel.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _visiblePassword = MutableLiveData(false)
    val visiblePassword: LiveData<Boolean> = _visiblePassword

    private val _mailInput = MutableLiveData<String>()
    val mailInput: LiveData<String> = _mailInput

    private val _passwordInput = MutableLiveData<String>()
    val passwordInput: LiveData<String> = _passwordInput

    fun changePasswordVisibility() {
        _visiblePassword.value = !_visiblePassword.value!!
    }

    fun updateDate(data: String, input: String) {
        when(input) {
            "password" -> {
                _passwordInput.value = data
            }
            "mail" -> {
                _mailInput.value = data
            }
        }
    }
}