package com.upaep.colegios.model.repository.grades

import com.upaep.colegios.model.api.grades.GradesService
import javax.inject.Inject

class GradesRepository @Inject constructor(private val gradesService: GradesService) {
    suspend fun getGrades() {
        gradesService.getGrades()
    }
}