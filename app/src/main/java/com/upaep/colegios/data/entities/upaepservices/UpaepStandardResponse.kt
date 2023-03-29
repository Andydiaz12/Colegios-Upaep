package com.upaep.colegios.data.entities.upaepservices

data class UpaepStandardResponse(
    val CRYPTDATA: String = "cryptdataError",
    val error: Boolean = true,
    val statusCode: Int = 500,
    val message: String = "Error"
)
