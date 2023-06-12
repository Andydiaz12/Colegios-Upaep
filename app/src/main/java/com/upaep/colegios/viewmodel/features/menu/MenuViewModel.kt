package com.upaep.colegios.viewmodel.features.menu

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.upaep.colegios.view.base.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {

    fun closeSession(navigation: NavHostController) {
//        UserPreferences.getInstance(application.applicationContext).setLogged(false)
        navigation.navigate(Routes.LoginScreen.routes)
    }
}