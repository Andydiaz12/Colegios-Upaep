package com.upaep.colegios.data.entities.grades

import com.google.gson.annotations.SerializedName

data class GradesTypes(
    @SerializedName("PROMEDIO_GENERAL")
    val generalAvg: String,
    @SerializedName("BOLETAS")
    val reportCard: String
)
