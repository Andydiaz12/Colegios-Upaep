package com.upaep.colegios.model.entities.payments

data class PaymentMethod(
    val id : Int,
    val name: String? = null,
    val description: String? = null,
    val options: List<PaymentOptions> = emptyList()
)
