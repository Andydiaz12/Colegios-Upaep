package com.upaep.colegios.model.entities.grades

import com.google.gson.annotations.SerializedName

data class PartialGrade(
    @SerializedName("MATERIAS")
    val classes: List<SingleClass>,
    @SerializedName("PARCIAL")
    val partial: Int,
    @SerializedName("PROMEDIO")
    val average: Float
)
