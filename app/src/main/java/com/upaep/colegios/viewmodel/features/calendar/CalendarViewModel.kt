package com.upaep.colegios.viewmodel.features.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upaep.colegios.data.entities.calendar.CalendarConfiguration
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.DateFormatSymbols
import java.util.*
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class CalendarViewModel @Inject constructor() : ViewModel() {
    private val _calendarConfiguration = MutableLiveData(getCalendar())
    val calendarConfiguration: LiveData<CalendarConfiguration> = _calendarConfiguration
    private val actualMonth: Int = _calendarConfiguration.value!!.monthInInt

    fun getCalendarConfiguration(): CalendarConfiguration {
        return _calendarConfiguration.value!!
    }

    fun getActualMonth(): Int = actualMonth
    fun getOtherMonth(movement: String) {
        val operator = when (movement) {
            "prev" -> -1
            "next" -> 1
            else -> 0
        }
        _calendarConfiguration.value!!.monthInInt += operator
        if (_calendarConfiguration.value!!.monthInInt < 0) {
            _calendarConfiguration.value!!.monthInInt = 11
            _calendarConfiguration.value!!.yearInInt--
        } else if (_calendarConfiguration.value!!.monthInInt > 11) {
            _calendarConfiguration.value!!.monthInInt = 0
            _calendarConfiguration.value!!.yearInInt++
        }
        _calendarConfiguration.value = getCalendar(
            customMonth = _calendarConfiguration.value!!.monthInInt,
            customYear = _calendarConfiguration.value!!.yearInInt
        )
    }

    private fun getCalendar(
        customMonth: Int? = null,
        customYear: Int? = null
    ): CalendarConfiguration {
        val calendarInstance = Calendar.getInstance()
        val year = customYear ?: calendarInstance.get(Calendar.YEAR)
        val month = customMonth ?: calendarInstance.get(Calendar.MONTH)
        val symbols = DateFormatSymbols.getInstance()
        val monthName = symbols.months[month].replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
        }
        val cal = calendarInstance.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val firstDayOfMonth = cal.get(Calendar.DAY_OF_WEEK)
        val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        val startDay = Calendar.SUNDAY
        val numRows = ceil((firstDayOfMonth + daysInMonth - 1) / 7.0).toInt()
        val daysMatrix = Array(numRows) { IntArray(7) }
        cal.set(Calendar.DAY_OF_WEEK, startDay)
        for (row in 0 until numRows) {
            for (col in 0 until 7) {
                if (cal.get(Calendar.MONTH) == month) {
                    daysMatrix[row][col] = cal.get(Calendar.DAY_OF_MONTH)
                }
                cal.add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        return CalendarConfiguration(
            numRows = numRows,
            startDay = startDay,
            daysMatrix = daysMatrix,
            monthName = monthName,
            monthInInt = month,
            yearInInt = year
        )
    }
}