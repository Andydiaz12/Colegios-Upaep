package com.upaep.colegios.data.domain.schedule

import com.upaep.colegios.data.entities.schedule.DayClass
import com.upaep.colegios.data.repository.schedule.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(perseq: String, persclv: String): List<DayClass> {
        return scheduleRepository.getSchedule(perseq = perseq, persclv = persclv)
    }
}