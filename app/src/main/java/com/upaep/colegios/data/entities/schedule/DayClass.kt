package com.upaep.colegios.data.entities.schedule

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class DayClass(
    @PrimaryKey
    @SerializedName("CLAVE")
    val clave: String,
    @SerializedName("GRUPO")
    val group: String? = null,
    @SerializedName("ASIGNATURA")
    val className: String = "",
    @SerializedName("TIPO")
    val type: String? = null,
    @SerializedName("CATEDRATICO")
    val teacherName: String? = null,
    @SerializedName("PERS_CLV")
    val pers_clv: String? = null,
    @SerializedName("CAPR_CLV")
    val capr_clv: String? = null,
    @SerializedName("GACU_CLAVE")
    val gacu_clave: String? = null,
    @SerializedName("ASPR_SEQ")
    val aspr_seq: String? = null,
    @SerializedName("CAPR_DESC")
    val capr_desc: String? = null,
    val lunes: String? = null,
    val martes: String? = null,
    val miercoles: String? = null,
    val jueves: String? = null,
    val viernes: String? = null
)
