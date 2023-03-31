package com.upaep.colegios.data.entities.calendar

data class CalendarConfiguration(
    val numRows: Int,
    val daysMatrix: Array<IntArray>,
    val startDay: Int,
    val monthName: String,
    var monthInInt: Int,
    var yearInInt: Int
)
