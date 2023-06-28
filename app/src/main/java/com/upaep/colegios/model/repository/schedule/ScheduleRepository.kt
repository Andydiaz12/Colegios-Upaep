package com.upaep.colegios.model.repository.schedule

import com.upaep.colegios.model.api.schedule.ScheduleService
import com.upaep.colegios.model.database.scheduledao.ScheduleDao
import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.model.entities.locksmith.IDDKeychain
import com.upaep.colegios.model.entities.schedule.DayClass
import com.upaep.colegios.model.repository.locksmith.LocksmithRepository
import javax.inject.Inject

class ScheduleRepository @Inject constructor(
    private val scheduleDao: ScheduleDao,
    private val scheduleService: ScheduleService,
    private val locksmithRepository: LocksmithRepository
) {
    suspend fun getSchedule(persclv: String, perseq: String): List<DayClass> {
        val schedule = scheduleService.getSchedule(
            persclv = persclv,
            perseq = perseq,
            keyChain = locksmithRepository.getLockSmith().colegiosUpaepAcademico!!
        )
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