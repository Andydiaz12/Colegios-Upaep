package com.upaep.colegios.viewmodel.features.splash

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.upaep.colegios.data.base.preferences.UserPreferences
import com.upaep.colegios.data.domain.base.GetSessionUseCase
import com.upaep.colegios.data.domain.splash.GetLockSmithUseCase
import com.upaep.colegios.data.entities.AnswerBack
import com.upaep.colegios.view.base.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val application: Application,
    private val getLockSmithUseCase: GetLockSmithUseCase,
//    private val getSessionUseCase: GetSessionUseCase
) : ViewModel() {
    lateinit var navigation: NavHostController
    init {
        viewModelScope.launch {
            when(getLockSmithUseCase()) {
                is AnswerBack.Success -> {
                    val session = UserPreferences.getInstance(context = application.applicationContext).isLogged()
                    val destination = if(session) {
                        Routes.StudentSelectorScreen.routes
                    } else {
                        Routes.LoginScreen.routes
                    }
                    navigation.navigate(destination)
                }
                else -> {
                    Log.i("errorLocksmith", ":(")
                }
            }
        }
    }
}