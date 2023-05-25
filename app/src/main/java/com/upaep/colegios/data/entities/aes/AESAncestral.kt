package com.upaep.colegios.data.entities.aes

import com.upaep.colegios.BuildConfig

object AESAncestral: AESKeychain(
    blockSize = BuildConfig.LOCKSMITH_AES_BLOCK_SIZE,
    key = BuildConfig.LOCKSMITH_AES_KEY,
    iv = BuildConfig.LOCKSMITH_AES_IV,
    inputKey = null
) {
}