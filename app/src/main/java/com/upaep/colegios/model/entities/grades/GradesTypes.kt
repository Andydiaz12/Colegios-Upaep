package com.upaep.colegios.model.entities.grades

import com.google.gson.annotations.SerializedName

data class GradesTypes(
    @SerializedName("NOMBRE")
    val name: String,
    @SerializedName("TIPO")
    val type: String,
    @SerializedName("PARCIALES")
    var partials: List<PartialGrade>
)
