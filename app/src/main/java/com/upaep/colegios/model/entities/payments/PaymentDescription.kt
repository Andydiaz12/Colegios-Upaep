package com.upaep.colegios.model.entities.payments

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PaymentDescription(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val movaHperPersClv: String,
    val movaHperSeq: String,
    val fechamov: String,
    val ctsrDescrAl: String,
    val movaImporte: String,
    val keysat: String,
    val url: String,
    val fgblSerie: String,
    val fgenNofactura: String,
    val persRfc: String
)
