package com.upaep.colegios.data.domain.grades

import com.upaep.colegios.data.repository.grades.GradesRepository
import javax.inject.Inject

class GetGradesUseCase @Inject constructor(private val gradesRepository: GradesRepository) {
    suspend operator fun invoke() {
        gradesRepository.getGrades()
    }
}