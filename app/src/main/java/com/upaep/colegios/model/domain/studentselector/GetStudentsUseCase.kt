package com.upaep.colegios.model.domain.studentselector

import com.upaep.colegios.model.entities.studentselector.StudentsSelector
import com.upaep.colegios.model.repository.studentselector.StudentSelectorRepository
import javax.inject.Inject

class GetStudentsUseCase @Inject constructor(private val studentSelectorRepository: StudentSelectorRepository) {
    suspend operator fun invoke() : List<StudentsSelector> {
        return studentSelectorRepository.getStudents()
    }
}