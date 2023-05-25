package com.upaep.colegios.data.entities.aes

import com.google.gson.annotations.SerializedName

open class AESKeychain {
    @SerializedName("AES_BLOCKSIZE")
    var blockSize: Int? = 256

    @SerializedName("AES_KEY")
    var key: String? = null

    @SerializedName("AES_INPUT_KEY")
    var inputKey: String? = null

    @SerializedName("AES_IV")
    var iv: String? = null

    constructor(key: String?, iv: String, blockSize: Int, inputKey: String?) {

        this.key = key
        this.iv = iv
        this.blockSize = blockSize
        this.inputKey = inputKey
    }

    override fun toString(): String {
        return "AESKeychain(blockSize=$blockSize, key=$key, iv=$iv, inputKey=$inputKey)"
    }
}
