package com.upaep.colegios.data.entities.locksmith

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class IDDKeychain(
    @SerializedName("AES")
    @Embedded
    var AESKeychain: AESKeychain? = null,

    @Embedded
    @SerializedName("JWT")
    var JWTKeychain: JWTKeychain? = null
)