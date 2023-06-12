package com.upaep.colegios.model.repository.schedule

import com.upaep.colegios.model.api.schedule.ScheduleService
import com.upaep.colegios.model.database.scheduledao.ScheduleDao
import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.model.entities.schedule.DayClass
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