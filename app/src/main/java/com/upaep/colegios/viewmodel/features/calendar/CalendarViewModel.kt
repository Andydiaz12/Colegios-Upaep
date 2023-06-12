package com.upaep.colegios.viewmodel.features.calendar

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upaep.colegios.model.entities.calendar.CalendarConfiguration
import com.upaep.colegios.model.entities.calendar.GoogleEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class CalendarViewModel @Inject constructor(
    application: Application,
) : ViewModel() {
    private val _calendarConfiguration = MutableLiveData(getCalendar())
    private val _calendarEvents = MutableLiveData<List<GoogleEvents>>()
    private val actualMonth: Int = _calendarConfiguration.value!!.monthInInt
    private val actualDay: Int = Calendar.getInstance().get(Calendar.DATE)
    private val actualYear: Int = Calendar.getInstance().get(Calendar.YEAR)
    private val _daysWithEvents = MutableLiveData(MutableList(35) { 0 })
//    private val userPreferences =
//        UserPreferences.getInstance(context = application.applicationContext)
    val calendarConfiguration: LiveData<CalendarConfiguration> = _calendarConfiguration
    val calendarEvents: LiveData<List<GoogleEvents>> = _calendarEvents
    val daysWithEvents: LiveData<MutableList<Int>> = _daysWithEvents
//    val childName: LiveData<StudentsSelector> = MutableLiveData(userPreferences.getChildSelectedData())
//    val levelColor: LiveData<Color> = MutableLiveData(Color(userPreferences.getColor()))

    init {
        fillCalendarData()
    }

    fun fillCalendarData() {
        //Consumir servicio de Google para eventos
        val events = getEvents()
        _daysWithEvents.value = MutableList(35) { 0 }
        events.forEach() { event ->
            event.startDateDesc = getParsedDate(event.startDate!!)
            event.endDateDesc = getParsedDate(event.endDate!!)
            val startDateSplit = event.startDate!!.split(" ")
            val endDateSplit = event.endDate!!.split(" ")
            val startDate = startDateSplit[2].toInt()
            val endDate = endDateSplit[2].toInt()
            for (i in startDate..endDate) {
                _daysWithEvents.value!![i] = 1
            }
        }
        _calendarEvents.value = events
    }

    fun getCalendarConfiguration(): CalendarConfiguration {
        return _calendarConfiguration.value!!
    }

    fun getIfEventsInDay(selectedDay: Int): Boolean = _daysWithEvents.value!![selectedDay] == 0
    fun getActualDay(): Int = actualDay
    fun getActualYear(): Int = actualYear
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
        fillCalendarData()
    }

    fun getParsedDate(date: String): String {
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
        val outputFormat = SimpleDateFormat("d MMMM", Locale("es", "MX"))
        val parsedDate = inputFormat.parse(date)
        return outputFormat.format(parsedDate!!)
    }

    fun dayWithinRange(selectedDay: Int, event: GoogleEvents): Boolean {
        val startDate = event.startDate?.split(" ")!![2].toInt()
        val endDate = event.endDate?.split(" ")!![2].toInt()
        return selectedDay in startDate..endDate
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
            monthNumber = month,
            monthName = monthName,
            monthInInt = month,
            yearInInt = year
        )
    }

    fun getEvents(): List<GoogleEvents> {
        return listOf(
            GoogleEvents(
                summary = "Suspensión de clases y labores / Día del Trabajo",
                startDate = "Mon May 01 00:00:00 CDT 2023",
                endDate = "Tue May 02 00:00:00 CDT 2023"
            ),
            GoogleEvents(
                summary = "Tercer parcial Primavera 2023 (captura faltas y calificaciones)",
                startDate = "Tue May 02 00:00:00 CDT 2023",
                endDate = "Mon May 08 00:00:00 CDT 2023"
            ),
            GoogleEvents(
                summary = "Autoevaluación docente y autoevaluación del estudiante Primavera 2023",
                startDate = "Tue May 02 00:00:00 CDT 2023",
                endDate = "Mon May 03 00:00:00 CDT 2023"
            ),
            GoogleEvents(
                summary = "Suspensión de clases y labores / Aniversario de la Batalla de Puebla",
                startDate = "Fri May 05 00:00:00 CDT 2023",
                endDate = "Sat May 06 00:00:00 CDT 2023"
            ),
            GoogleEvents(
                summary = "50 Aniversario UPAEP",
                startDate = "Sun May 07 00:00:00 CDT 2023",
                endDate = "Mon May 08 00:00:00 CDT 2023"
            ),
            GoogleEvents(
                summary = "Correcciones tercer parcial Primavera 2023 (faltas y calificaciones)",
                startDate = "Mon May 08 00:00:00 CDT 2023",
                endDate = "Sat May 11 00:00:00 CDT 2023"
            ),
            GoogleEvents(
                summary = "Evaluación del profesor tutor Primavera 2023",
                startDate = "Mon May 15 00:00:00 CDT 2023",
                endDate = "Mon May 18 00:00:00 CDT 2023"
            )
        )
    }
}