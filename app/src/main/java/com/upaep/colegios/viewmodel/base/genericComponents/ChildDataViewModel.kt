package com.upaep.colegios.viewmodel.base.genericComponents

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.colegios.model.base.UserPreferences
import com.upaep.colegios.model.entities.studentselector.StudentsSelector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChildDataViewModel @Inject constructor(application: Application) : ViewModel() {

    private val userPreferences = UserPreferences(application.applicationContext)

    fun changeSelectedStudent(student: StudentsSelector, color: Color) {
        viewModelScope.launch {
            userPreferences.setSelectedStudent(studentSelected = student, color = color)
        }
    }

}