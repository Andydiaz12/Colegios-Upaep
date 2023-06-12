package com.upaep.colegios.model.entities.studentselector

data class GetStudentModel(
    val IDS: List<StudentsIds>,
    val ORIGEN: String = "15"
)
