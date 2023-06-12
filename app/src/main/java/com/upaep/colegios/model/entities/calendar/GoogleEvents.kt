package com.upaep.colegios.model.entities.calendar

data class GoogleEvents(
    var summary: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var startDateDesc: String ?= null,
    var endDateDesc: String ?= null
)
