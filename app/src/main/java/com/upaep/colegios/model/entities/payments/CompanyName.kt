package com.upaep.colegios.model.entities.payments

data class CompanyName(
    val name: String,
    val taxRegime: String,
    val rfc: String,
    val mail: String,
    val address: String,
    val zipCode: String,
    val state: String
)
