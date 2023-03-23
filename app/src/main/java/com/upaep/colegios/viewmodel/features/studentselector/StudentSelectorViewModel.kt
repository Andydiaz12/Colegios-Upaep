package com.upaep.colegios.viewmodel.features.studentselector

import androidx.lifecycle.ViewModel
import com.upaep.colegios.data.entities.studentselector.StudentsSelector
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudentSelectorViewModel @Inject constructor() : ViewModel() {
    fun getStudents(): List<StudentsSelector> {
        return listOf(
            StudentsSelector(
                name = "Perenganito Ramirez Gutierrez",
                level = "Preescolar",
                group = "3ºB"
            ),
            StudentsSelector(name = "Fulanito Pérez López", level = "Secundaria", group = "2ºA"),
            StudentsSelector(name = "Hermenegilda Pérez López", level = "Primaria", group = "1ºC"),
            StudentsSelector(name = "Jorge Ramirez Gutierrez", level = "Primaria", group = "1ºC"),
        )
    }
}