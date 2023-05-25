package com.upaep.colegios.data.repository.studentselector

import android.util.Log
import com.upaep.colegios.data.api.studentselector.StudentSelectorService
import com.upaep.colegios.data.database.studentselectordao.StudentSelectorDao
import com.upaep.colegios.data.entities.AnswerBack
import com.upaep.colegios.data.entities.studentselector.StudentsSelector
import com.upaep.colegios.view.base.uistate.CollegesUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentSelectorRepository @Inject constructor(
    private val studentSelectorService: StudentSelectorService,
    private val studentSelectorDao: StudentSelectorDao
) {

    val students: Flow<List<StudentsSelector>> = getStudentsFromService()

    fun getStudentsFromService(): Flow<List<StudentsSelector>> = flow {
        when (val answerBack = studentSelectorService.getStudents()) {
            is AnswerBack.Success -> {
                studentSelectorDao.deleteStudents()
                studentSelectorDao.insertStudents(answerBack.data)
                emit(studentSelectorDao.getStudents().first())
            }
            is AnswerBack.Error -> {

            }
            else -> {}
        }
    }

    suspend fun insertStudent(student: StudentsSelector) {
        studentSelectorDao.insertStudents(listOf(student))
    }
}