package com.upaep.colegios.data.domain.studentselector

import com.upaep.colegios.data.entities.studentselector.StudentsSelector
import com.upaep.colegios.data.repository.studentselector.StudentSelectorRepository
import javax.inject.Inject

class InsertStudentUseCase @Inject constructor(private val studentSelectorRepository: StudentSelectorRepository) {
    suspend operator fun invoke(student: StudentsSelector) {
        studentSelectorRepository.insertStudent(student)
    }
}