package com.upaep.colegios.viewmodel.features.splash

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.upaep.colegios.model.base.UserPreferences
import com.upaep.colegios.model.domain.splash.GetLockSmithUseCase
import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.view.base.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val application: Application,
    private val getLockSmithUseCase: GetLockSmithUseCase,
//    private val getSessionUseCase: GetSessionUseCase
) : ViewModel() {
    lateinit var navigation: NavHostController

    val userPreferences = UserPreferences(application.applicationContext)

    init {
        viewModelScope.launch {
            when (getLockSmithUseCase()) {
                is AnswerBack.Success -> {
                    val session = userPreferences.isLogged()
                    val destination = if (session) {
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