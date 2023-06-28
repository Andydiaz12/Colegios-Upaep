package com.upaep.colegios.model.domain.schedule

import com.upaep.colegios.model.entities.schedule.DayClass
import com.upaep.colegios.model.repository.locksmith.LocksmithRepository
import com.upaep.colegios.model.repository.schedule.ScheduleRepository
import javax.inject.Inject

class GetScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(perseq: String, persclv: String): List<DayClass> {
        return scheduleRepository.getSchedule(
            perseq = perseq,
            persclv = persclv
        )
    }
}