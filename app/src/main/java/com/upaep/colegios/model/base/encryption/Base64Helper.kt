package com.upaep.colegios.model.base.encryption

object Base64Helper {
    fun getBase64(str: String): String {
        return String(
            android.util.Base64.encode(
                str.toByteArray(),
                android.util.Base64.DEFAULT
            )
        )
    }
}