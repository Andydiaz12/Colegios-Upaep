package com.upaep.colegios.data.domain.studentselector

import com.upaep.colegios.data.entities.studentselector.StudentsSelector
import com.upaep.colegios.data.repository.studentselector.StudentSelectorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStudentsUseCase @Inject constructor(private val studentSelectorRepository: StudentSelectorRepository) {
    operator fun invoke() : Flow<List<StudentsSelector>> {
        return studentSelectorRepository.students
    }
}