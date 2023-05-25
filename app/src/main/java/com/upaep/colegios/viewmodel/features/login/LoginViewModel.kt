package com.upaep.colegios.viewmodel.features.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.upaep.colegios.data.base.preferences.UserPreferences
import com.upaep.colegios.data.domain.login.DoLoginUseCase
import com.upaep.colegios.view.base.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val application: Application,
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
        when (input) {
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
            //val execLogin = doLoginUseCase()
            //Si el login es exitoso hacer el siguiente bloque de código
            UserPreferences.getInstance(context = application.applicationContext).setLogged(value = true)
            navigation.navigate(Routes.OnBoardScreen.routes)
            //Si el login NO es exitoso hacer lo siguiente
        }
    }
}