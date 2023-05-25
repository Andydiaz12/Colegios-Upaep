package com.upaep.colegios.viewmodel.features.schedule

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.colegios.data.base.preferences.UserPreferences
import com.upaep.colegios.data.domain.schedule.GetScheduleUseCase
import com.upaep.colegios.data.entities.schedule.DayClass
import com.upaep.colegios.view.base.theme.Preschool_color
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class ScheduleViewModel @Inject constructor(
    application: Application,
    private val getScheduleUseCase: GetScheduleUseCase
) : ViewModel() {

    private val _scheduleList = MutableLiveData<List<DayClass>>(emptyList())
    private val userPreferences = UserPreferences.getInstance(application)
    private val studentData = userPreferences.getChildSelectedData()
    private val calendar = Calendar.getInstance()
    val scheduleList: LiveData<List<DayClass>> = _scheduleList
    val childName: LiveData<String> = MutableLiveData(userPreferences.getChildSelected())
    val levelColor: LiveData<Color> = MutableLiveData(Color(userPreferences.getColor()))
    val days = listOf("", "lunes", "martes", "miercoles", "jueves", "viernes")

    init {
        viewModelScope.launch {
            _scheduleList.value = getScheduleUseCase(perseq = "2", persclv = "3519671")
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
        val hourRangeTwo = "13:00 - 14:00"
//        val splittedHour = hourRange.split("-")
        val splittedHour = hourRangeTwo.split("-")
        val lowerHour = LocalTime.parse(splittedHour[0].trim())
        val upperHour = LocalTime.parse(splittedHour[1].trim())
        val currentTime = LocalTime.now()
        return Pair(
            "${splittedHour[0]} a ${splittedHour[1]}",
            currentTime in lowerHour..upperHour && days[calendar.get(Calendar.DAY_OF_WEEK) - 1] == selectedDay
        )
    }
}