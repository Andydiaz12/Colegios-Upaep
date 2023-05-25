package com.upaep.colegios.data.entities.locksmith

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class JWTKeychain (
    @SerializedName("API_KEY")
    var apiKey: String? = null,

    @SerializedName("JWT_KEY")
    var jwtKey: String? = null
)