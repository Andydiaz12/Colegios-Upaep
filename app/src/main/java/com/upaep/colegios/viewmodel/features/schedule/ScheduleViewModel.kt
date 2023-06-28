package com.upaep.colegios.viewmodel.features.schedule

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.colegios.model.domain.schedule.GetScheduleUseCase
import com.upaep.colegios.model.entities.schedule.DayClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class ScheduleViewModel @Inject constructor(
    application: Application,
    private val getScheduleUseCase: GetScheduleUseCase
) : ViewModel() {

    private val _scheduleList = MutableLiveData<List<DayClass>>(emptyList())

    //    private val userPreferences = UserPreferences.getInstance(application)
    private val calendar = Calendar.getInstance()
    val scheduleList: LiveData<List<DayClass>> = _scheduleList
    val days = listOf("", "lunes", "martes", "miercoles", "jueves", "viernes")

    init {
        viewModelScope.launch {
            _scheduleList.value = getScheduleUseCase(perseq = "2", persclv = "3519671")
//            _scheduleList.value = getSchedule()
        }
    }

    fun dayHour(dayClass: DayClass, selectedDay: String): String? {
        return dayClass::class.members.find { it.name == selectedDay }
            ?.call(dayClass) as? String
    }

    @SuppressLint("NewApi")
    fun getHour(
        hourRange: String,
        selectedDay: String
    ): Pair<String, Boolean> {
        val splittedHour = hourRange.split("-")
        val lowerHourTrim = splittedHour[0].trim()
        val upperHourTrim = splittedHour[1].trim()
        val lowerHour =
            LocalTime.parse(if (lowerHourTrim.split(":")[0].length > 1) lowerHourTrim else "0${lowerHourTrim}")
        val upperHour =
            LocalTime.parse(if (upperHourTrim.split(":")[0].length > 1) upperHourTrim else "0${upperHourTrim}")
        val currentTime = LocalTime.now()
        return Pair(
            "${splittedHour[0]} a ${splittedHour[1]}",
            currentTime in lowerHour..upperHour && days[calendar.get(Calendar.DAY_OF_WEEK) - 1] == selectedDay
        )
    }

    fun getSchedule(): List<DayClass> {
        return listOf(
            DayClass(clave = "COM013", className = "Materia Lunes", lunes = "8:30-10:00--"),
            DayClass(clave = "COM014", className = "Materia Lunes", lunes = "10:00-16:30--"),
            DayClass(clave = "COM015", className = "Materia Martes", martes = "8:30-10:00--"),
        )
    }
}