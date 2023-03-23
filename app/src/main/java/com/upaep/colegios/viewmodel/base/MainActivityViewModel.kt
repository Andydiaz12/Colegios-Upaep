package com.upaep.colegios.viewmodel.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upaep.colegios.view.base.theme.ThemeSchema
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    private val _theme = MutableLiveData<ThemeSchema>(ThemeSchema.LIGHT)
    val theme: LiveData<ThemeSchema> = _theme

    fun getTheme(): ThemeSchema = _theme.value!!
}