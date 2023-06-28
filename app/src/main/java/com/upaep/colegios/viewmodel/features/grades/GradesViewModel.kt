package com.upaep.colegios.viewmodel.features.grades

import android.util.MutableInt
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.colegios.model.domain.grades.GetGradesUseCase
import com.upaep.colegios.model.entities.grades.GeneralGrades
import com.upaep.colegios.model.entities.grades.GradesTypes
import com.upaep.colegios.model.entities.grades.PartialGrade
import com.upaep.colegios.model.entities.grades.SingleClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GradesViewModel @Inject constructor(getGradesUseCase: GetGradesUseCase) : ViewModel() {

    private val _grades = MutableLiveData<GeneralGrades>()
    val grades: LiveData<GeneralGrades> = _grades

//    init {
//        viewModelScope.launch {
//            getGradesUseCase()
//        }
//    }

    fun getSelectedPartial() = GradesData.getSelectedPartial()

    fun selectedPartial(selection: Int) {
        GradesData.selectedPartial(selection = selection)
    }

    fun getSavedGrades() {
        _grades.value = GradesData.getGrades()
    }

    fun getGradesData() {
        //Se consume el servicio de obtener las calificaciones
        //Provicional pondremos el de full response
        val gradesResponse = fullResponse()
        for (reportCard in gradesResponse.reportCard) {
            reportCard.partials = reportCard.partials.sortedBy { partial -> partial.partial }
        }
        GradesData.setGrades(gradesResponse)
        getSavedGrades()
    }

    fun fullResponse(): GeneralGrades {
        return GeneralGrades(
            generalAvg = "1",
            reportCard = listOf(
                GradesTypes(
                    name = "ASIGNATURAS DE FORMACIÓN ACADMÉMICAS",
                    type = "B",
                    partials = getData()
                ),
                GradesTypes(name = "ASIGNATURAS FORMATIVAS", type = "B", partials = getData())
            )
        )
    }

    fun getData(): List<PartialGrade> {
        return listOf(
            PartialGrade(
                classes = listOf(
                    SingleClass(className = "HISTORIA", score = 3.3f, absences = 2),
                    SingleClass(className = "SEGUNDA LENGUA (INGLÉS)", score = 3.3f, absences = 2),
                    SingleClass(className = "GEOGRAFIA", score = 3.3f, absences = 2),
                    SingleClass(className = "EDUCACIÓN FÍSICA", score = 3.3f, absences = 2),
                    SingleClass(className = "FORMACIÓN CIVICA Y ÉTICA", score = 3.3f, absences = 2),
                    SingleClass(className = "CIENCIAS NATURALES", score = 3.3f, absences = 2),
                    SingleClass(className = "EDUCACION ARTISTICA", score = 3.3f, absences = 2),
                    SingleClass(className = "MATEMATICAS", score = 3.3f, absences = 2)
                ), partial = 3, average = 7.2f
            ),
            PartialGrade(
                classes = listOf(
                    SingleClass(className = "HISTORIA", score = 6.7f, absences = 3),
                    SingleClass(className = "SEGUNDA LENGUA (INGLÉS)", score = 5f, absences = 3),
                    SingleClass(className = "GEOGRAFIA", score = 7.1f, absences = 3),
                    SingleClass(className = "EDUCACIÓN FÍSICA", score = 8.5f, absences = 3),
                    SingleClass(className = "FORMACIÓN CIVICA Y ÉTICA", score = 7.6f, absences = 3),
                    SingleClass(className = "CIENCIAS NATURALES", score = 7.4f, absences = 3),
                    SingleClass(className = "EDUCACION ARTISTICA", score = 8f, absences = 3),
                    SingleClass(className = "MATEMATICAS", score = 6.8f, absences = 3)
                ), partial = 4, average = 7.2f
            ),
            PartialGrade(
                classes = listOf(
                    SingleClass(className = "HISTORIA", score = 1.1f, absences = 0),
                    SingleClass(className = "SEGUNDA LENGUA (INGLÉS)", score = 1.1f, absences = 0),
                    SingleClass(className = "GEOGRAFIA", score = 1.1f, absences = 0),
                    SingleClass(className = "EDUCACIÓN FÍSICA", score = 1.1f, absences = 0),
                    SingleClass(className = "FORMACIÓN CIVICA Y ÉTICA", score = 1.1f, absences = 0),
                    SingleClass(className = "CIENCIAS NATURALES", score = 1.1f, absences = 0),
                    SingleClass(className = "EDUCACION ARTISTICA", score = 1.1f, absences = 0),
                    SingleClass(className = "MATEMATICAS", score = 1.1f, absences = 0)
                ), partial = 1, average = 7.2f
            ),
            PartialGrade(
                classes = listOf(
                    SingleClass(className = "HISTORIA", score = 2.2f, absences = 1),
                    SingleClass(className = "SEGUNDA LENGUA (INGLÉS)", score = 2.2f, absences = 1),
                    SingleClass(className = "GEOGRAFIA", score = 2.2f, absences = 1),
                    SingleClass(className = "EDUCACIÓN FÍSICA", score = 2.2f, absences = 1),
                    SingleClass(className = "FORMACIÓN CIVICA Y ÉTICA", score = 2.2f, absences = 1),
                    SingleClass(className = "CIENCIAS NATURALES", score = 2.2f, absences = 1),
                    SingleClass(className = "EDUCACION ARTISTICA", score = 2.2f, absences = 1),
                    SingleClass(className = "MATEMATICAS", score = 2.2f, absences = 1)
                ), partial = 2, average = 7.2f
            ),
            PartialGrade(
                classes = listOf(
                    SingleClass(className = "HISTORIA", score = 6.7f, absences = 4),
                    SingleClass(className = "SEGUNDA LENGUA (INGLÉS)", score = 5f, absences = 4),
                    SingleClass(className = "GEOGRAFIA", score = 7.1f, absences = 4),
                    SingleClass(className = "EDUCACIÓN FÍSICA", score = 8.5f, absences = 4),
                    SingleClass(className = "FORMACIÓN CIVICA Y ÉTICA", score = 7.6f, absences = 4),
                    SingleClass(className = "CIENCIAS NATURALES", score = 7.4f, absences = 4),
                    SingleClass(className = "EDUCACION ARTISTICA", score = 8f, absences = 4),
                    SingleClass(className = "MATEMATICAS", score = 6.8f, absences = 4)
                ), partial = 5, average = 7.2f
            ),
        )
    }
}