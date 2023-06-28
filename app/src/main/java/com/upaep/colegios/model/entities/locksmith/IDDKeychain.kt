package com.upaep.colegios.model.entities.locksmith

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class IDDKeychain(
    @SerializedName("AES")
    @Embedded
    var AESKeychain: AESKeychain? = null,

    @Embedded
    @SerializedName("JWT")
    var JWTKeychain: JWTKeychain? = null
)