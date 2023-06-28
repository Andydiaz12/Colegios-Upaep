package com.upaep.colegios.model.entities.accountbalance

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountBalance(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val hperPersClv: String,
    val hperSeq: String,
    val edrsOrden: String,
    val edrsConcepto: String,
    val edrsCargo: String,
    val edrsRecargo: String,
    var edrsPago: String,
    val edrsSaldo: String,
    val edrsFchVen: String?,
    val edrsFchPag: String?,
    val edrsEstatus: String,
    val edrsDescEstatus: String,
    val edrsParcialidad: String?,
    val ctsrClv: String,
    val edrsReferencia: String
)
