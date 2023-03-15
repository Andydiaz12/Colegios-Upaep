package com.upaep.colegios.viewmodel.Features.login

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upaep.colegios.R
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

    private val _titleText = MutableLiveData(R.string.blocked_account_title)
    val titleText: LiveData<Int> = _titleText

    private val _contentText = MutableLiveData(R.string.blocked_account_content)
    val contentText: LiveData<Int> = _contentText

    fun updateMail(email: String) {
        _email.value = email
    }

    fun getScreen(screen: String): Boolean {
        if (screen == "blockedAccount") {
            _titleText.value = R.string.blocked_account_title
            _contentText.value = R.string.blocked_account_content
            return true;
        } else {
            _titleText.value = R.string.forgot_password_title
            _contentText.value = R.string.forgot_password_content
            return false
        }
    }

    fun getTitleText() : Int {
        return _titleText.value!!
    }

    fun getContentText(): Int {
        return _contentText.value!!
    }
}