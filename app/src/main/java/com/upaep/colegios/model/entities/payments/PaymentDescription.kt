package com.upaep.colegios.model.entities.payments

data class PaymentDescription(
    val name: String,
    val description: String,
    val amount: String,
    var checked: Boolean = false
)
