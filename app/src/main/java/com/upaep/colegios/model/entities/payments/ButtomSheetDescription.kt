package com.upaep.colegios.model.entities.payments

data class ButtomSheetDescription(
    val header: String,
    val description: String,
    val buttonText: String,
    val onButtonClick: () -> Unit,
    val image: Int? = null
)
