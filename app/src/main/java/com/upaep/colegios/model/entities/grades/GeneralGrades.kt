package com.upaep.colegios.model.entities.grades

import com.google.gson.annotations.SerializedName

data class GeneralGrades(
    @SerializedName("PROMEDIO_GENERAL")
    val generalAvg: String,
    @SerializedName("BOLETAS")
    val reportCard: List<GradesTypes>
)
