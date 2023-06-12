package com.upaep.colegios.model.entities.payments

data class PaymentBankInstructions(
    val step: String? = null,
    val description: String,
    val extraContent: String? = null
)
