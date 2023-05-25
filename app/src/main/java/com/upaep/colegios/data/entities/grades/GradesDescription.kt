package com.upaep.colegios.data.entities.grades

import com.google.gson.annotations.SerializedName

data class GradesDescription(
    @SerializedName("PARCIAL")
    val partial: Int,
    @SerializedName("CALIFICACION")
    val grade: Int,
    @SerializedName("FALTAS")
    val absences: Int,
    @SerializedName("PROMEDIO_GEN_PARCIAL")
    val generalAvg: Float
)