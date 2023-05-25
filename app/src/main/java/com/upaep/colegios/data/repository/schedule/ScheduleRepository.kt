package com.upaep.colegios.data.repository.schedule

import android.util.Log
import com.upaep.colegios.data.api.schedule.ScheduleService
import com.upaep.colegios.data.database.scheduledao.ScheduleDao
import com.upaep.colegios.data.entities.AnswerBack
import com.upaep.colegios.data.entities.schedule.DayClass
import com.upaep.colegios.data.entities.studentselector.StudentsSelector
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScheduleRepository @Inject constructor(
    private val scheduleDao: ScheduleDao,
    private val scheduleService: ScheduleService
) {
    suspend fun getSchedule(persclv: String, perseq: String) : List<DayClass> {
        val schedule = scheduleService.getSchedule(persclv = persclv, perseq = perseq)
        return when (schedule) {
            is AnswerBack.Success -> {
                scheduleDao.deleteClasses()
                scheduleDao.insertClasses(schedule.data)
                schedule.data
            }
            else -> {
                emptyList()
            }
        }
    }
}