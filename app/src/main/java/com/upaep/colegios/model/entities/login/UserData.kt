package com.upaep.colegios.model.entities.login

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserData(
    @PrimaryKey
    var mail: String,
    var logged: Boolean
)
