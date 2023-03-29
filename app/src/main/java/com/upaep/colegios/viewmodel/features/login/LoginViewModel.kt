package com.upaep.colegios.viewmodel.features.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.upaep.colegios.data.domain.login.DoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val doLoginUseCase: DoLoginUseCase
) : ViewModel() {
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

    fun doLogin(navigation: NavHostController) {
        viewModelScope.launch {
            val execLogin = doLoginUseCase()
            Log.i("loginDebug", execLogin.toString())
            //navigation.navigate(Routes.OnBoardScreen.routes)
        }
    }
}