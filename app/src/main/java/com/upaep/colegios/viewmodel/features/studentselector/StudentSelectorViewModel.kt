package com.upaep.colegios.viewmodel.features.studentselector

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.upaep.colegios.model.base.UserPreferences
import com.upaep.colegios.model.domain.studentselector.GetStudentsUseCase
import com.upaep.colegios.model.domain.studentselector.InsertStudentUseCase
import com.upaep.colegios.model.entities.studentselector.StudentsSelector
import com.upaep.colegios.view.base.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentSelectorViewModel @Inject constructor(
    private val application: Application,
    private val getStudentsUseCase: GetStudentsUseCase,
    private val insertStudentUseCase: InsertStudentUseCase
) : ViewModel() {

    private val _students = MutableLiveData<List<StudentsSelector>>()
    val students: LiveData<List<StudentsSelector>> = _students

    init {
        viewModelScope.launch {
            val studentsList = getStudentsUseCase()
            studentsList.forEach { child ->
                child.school =
                    child.school.split(" ")[0].lowercase().replaceFirstChar { it.uppercase() }
            }
            _students.value = studentsList
        }
    }

    fun navigateToHomeScreen(
        navigation: NavHostController?,
        student: StudentsSelector,
        levelColor: Color
    ) {
        val userPreferences = UserPreferences(application.applicationContext)
        viewModelScope.launch {
            userPreferences.setSelectedStudent(student, levelColor)
            navigation?.navigate(Routes.HomeScreen.routes) {
                popUpTo(0)
            }
        }
    }
}