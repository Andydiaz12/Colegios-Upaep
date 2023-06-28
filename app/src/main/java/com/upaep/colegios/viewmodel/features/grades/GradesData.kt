package com.upaep.colegios.viewmodel.features.grades

import com.upaep.colegios.model.entities.grades.GeneralGrades

object GradesData {
    private lateinit var grades: GeneralGrades
    private var selectedPartial : Int = 0

    fun getSelectedPartial() = selectedPartial
    fun selectedPartial(selection: Int) {
        selectedPartial = selection - 1
    }

    fun getGrades() = grades
    fun setGrades(generalGrades: GeneralGrades) {
        grades = generalGrades
    }
}