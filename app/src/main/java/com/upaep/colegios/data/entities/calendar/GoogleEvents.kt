package com.upaep.colegios.data.entities.calendar

import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

data class GoogleEvents(
    var summary: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var startDateDesc: String ?= null,
    var endDateDesc: String ?= null
)
