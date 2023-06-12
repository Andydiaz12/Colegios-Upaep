package com.upaep.colegios.model.entities.locksmith

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Locksmith(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @Embedded
    @SerializedName("COLEGIOS_UPAEP_ACADEMICO")
    var colegiosUpaepAcademico: IDDKeychain? = null
)
