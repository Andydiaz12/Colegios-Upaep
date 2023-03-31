package com.upaep.colegios.data.entities.calendar

import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

data class GoogleEvents(
    val id: String
    /*val id:String,
    var summary:String?= null,
    @TypeConverters(EventDateConverter::class)
    @SerializedName("start")
    var startDate: EventDateApp?=null,
    @TypeConverters(EventDateConverter::class)
    @SerializedName("end")
    var endDate: EventDateApp?=null*/
)
