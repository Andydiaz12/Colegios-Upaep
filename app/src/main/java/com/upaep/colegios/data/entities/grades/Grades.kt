package com.upaep.colegios.data.entities.grades

import com.google.gson.annotations.SerializedName

data class Grade(
    @SerializedName("NOMBRE")
    var name: String,
    @SerializedName("CLAVE")
    var clv: String,
    @SerializedName("CALIFICACIONES")
    var score: List<GradesDescription>,
    @SerializedName("CF")
    var cf: String? = null
)
