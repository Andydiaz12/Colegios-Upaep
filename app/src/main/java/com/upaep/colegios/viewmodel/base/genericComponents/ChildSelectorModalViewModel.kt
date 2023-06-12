package com.upaep.colegios.viewmodel.base.genericComponents

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.colegios.model.domain.studentselector.GetStudentsUseCase
import com.upaep.colegios.model.entities.studentselector.StudentsSelector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChildSelectorModalViewModel @Inject constructor(
    application: Application,
    private val getStudentsUseCase: GetStudentsUseCase
) : ViewModel() {

    private val _students = MutableLiveData<List<StudentsSelector>>(emptyList())
    val students: LiveData<List<StudentsSelector>> = _students

    init {
        viewModelScope.launch {
            val studentsData = getStudentsUseCase()
            studentsData.forEach { student ->
                student.school =
                    student.school.split(" ")[0].lowercase().replaceFirstChar { it.uppercase() }
            }
            _students.value = studentsData
        }
    }

    fun updateChildSelected(student: StudentsSelector) {
//        _selectedStudentData.value = student
    }
}