package com.upaep.colegios.model.entities.payments

data class PaymentOptions(
    val name: String? = null,
    val image: List<PaymentOptionImageDescription>,
    val action: Boolean = false,
    val destination: Int? = null
)
