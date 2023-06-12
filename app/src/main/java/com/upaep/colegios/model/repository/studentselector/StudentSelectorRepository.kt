package com.upaep.colegios.model.repository.studentselector

import com.upaep.colegios.model.api.studentselector.StudentSelectorService
import com.upaep.colegios.model.database.studentselectordao.StudentSelectorDao
import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.model.entities.studentselector.StudentsSelector
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentSelectorRepository @Inject constructor(
    private val studentSelectorService: StudentSelectorService,
    private val studentSelectorDao: StudentSelectorDao
) {

    suspend fun getStudents(): List<StudentsSelector> {
        return when (val answerBack = studentSelectorService.getStudents()) {
            is AnswerBack.Success -> {
                studentSelectorDao.deleteStudents()
                val testingData = mutableListOf(
                    StudentsSelector(
                        name = "JOSÉ ANDRÉS",
                        paternSurname = "DÍAZ",
                        motherSurname = "ESCOBAR",
                        perseq = "2",
                        persclv = "3359791",
                        school = "PREESCOLAR",
                        group = "A",
                        grade = 2
                    )
                )
                testingData.addAll(answerBack.data)
//                studentSelectorDao.insertStudents(answerBack.data)
//                answerBack.data
                studentSelectorDao.insertStudents(testingData)
                testingData
            }

            is AnswerBack.Error -> {
                emptyList()
            }

            else -> {
                emptyList()
            }
        }
    }

    suspend fun insertStudent(student: StudentsSelector) {
        studentSelectorDao.insertStudents(listOf(student))
    }
}