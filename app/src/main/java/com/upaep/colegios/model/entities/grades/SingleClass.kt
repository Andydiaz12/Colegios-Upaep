package com.upaep.colegios.model.entities.grades

import com.google.gson.annotations.SerializedName

data class SingleClass(
    @SerializedName("NOMMATERIA")
    val className: String,
    @SerializedName("CALIF")
    val score: Float,
    @SerializedName("FALTAS")
    val absences: Int
)
