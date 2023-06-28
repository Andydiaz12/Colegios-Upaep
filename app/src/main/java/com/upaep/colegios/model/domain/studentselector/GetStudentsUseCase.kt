package com.upaep.colegios.model.domain.studentselector

import com.upaep.colegios.model.entities.studentselector.StudentsSelector
import com.upaep.colegios.model.repository.locksmith.LocksmithRepository
import com.upaep.colegios.model.repository.studentselector.StudentSelectorRepository
import javax.inject.Inject

class GetStudentsUseCase @Inject constructor(
    private val studentSelectorRepository: StudentSelectorRepository,
    private val locksmithRepository: LocksmithRepository
    ) {
    suspend operator fun invoke() : List<StudentsSelector> {
        return studentSelectorRepository.getStudents(keyChain = locksmithRepository.getLockSmith().colegiosUpaepAcademico!!)
    }
}