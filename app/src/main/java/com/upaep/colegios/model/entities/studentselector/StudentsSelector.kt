package com.upaep.colegios.model.entities.studentselector

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class StudentsSelector(
    @PrimaryKey
    @SerializedName("PERSCLV")
    val persclv: String,
    val img: Int? = null,
    @SerializedName("APP")
    val paternSurname: String? = "",
    @SerializedName("APM")
    val motherSurname: String = "",
    @SerializedName("PERSEQ")
    val perseq: String,
    @SerializedName("GRUPO")
    val group: String? = null,
    @SerializedName("GRADO")
    val grade: Int? = null,
    @SerializedName("NOMBRE")
    val name: String,
    @SerializedName("ESCUELA")
    var school: String
) {
    init {
        this.school = school.split(" ")[0]
    }
}
