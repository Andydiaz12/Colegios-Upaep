package com.upaep.colegios.viewmodel.features.studentselector

import android.app.Application
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.gson.annotations.SerializedName
import com.upaep.colegios.data.base.preferences.UserPreferences
import com.upaep.colegios.data.domain.studentselector.GetStudentsUseCase
import com.upaep.colegios.data.domain.studentselector.InsertStudentUseCase
import com.upaep.colegios.data.entities.studentselector.StudentsSelector
import com.upaep.colegios.view.base.navigation.Routes
import com.upaep.colegios.view.base.theme.Preschool_color
import com.upaep.colegios.view.base.uistate.CollegesUiState
import com.upaep.colegios.view.base.uistate.CollegesUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentSelectorViewModel @Inject constructor(
    private val application: Application,
    getStudentsUseCase: GetStudentsUseCase,
    private val insertStudentUseCase: InsertStudentUseCase
) : ViewModel() {

    val uiState: StateFlow<CollegesUiState> =
        getStudentsUseCase().map { Success(it) }.catch { CollegesUiState.Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CollegesUiState.Loading)

    fun navigateToHomeScreen(navigation: NavHostController?, student: StudentsSelector, levelColor: Color) {
        val userPreferences = UserPreferences.getInstance(application.applicationContext)
        userPreferences.setChildSelectedData(student)
        userPreferences.setChildSelected("${student.name} ${student.paternSurname} ${student.motherSurname}")
        userPreferences.setColor(levelColor.toArgb())
        navigation?.navigate(Routes.HomeScreen.routes)
    }
}