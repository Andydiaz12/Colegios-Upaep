package com.upaep.colegios.model.domain.grades

import com.upaep.colegios.model.repository.grades.GradesRepository
import javax.inject.Inject

class GetGradesUseCase @Inject constructor(private val gradesRepository: GradesRepository) {
    suspend operator fun invoke() {
        gradesRepository.getGrades()
    }
}