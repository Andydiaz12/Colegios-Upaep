package com.upaep.colegios.viewmodel.Features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginExtraViewModel @Inject constructor() : ViewModel() {
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _visibleMail = MutableLiveData(false)
    val visibleMail: LiveData<Boolean> = _visibleMail

    private val _sectionMenu = MutableLiveData<String>()
    val sectionMenu: LiveData<String> = _sectionMenu

    fun updateMail(email: String) {
        _email.value = email
    }

    fun getScreen(screen: String) : Boolean {
        return screen == "blockedAccount"
    }
}