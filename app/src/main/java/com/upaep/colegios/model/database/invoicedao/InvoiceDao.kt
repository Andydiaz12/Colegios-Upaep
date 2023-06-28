package com.upaep.colegios.model.database.invoicedao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.upaep.colegios.model.entities.payments.PaymentDescription

@Dao
interface InvoiceDao {
    @Insert
    suspend fun addInvoice(paymentDescription: List<PaymentDescription>)

    @Query("SELECT * FROM PaymentDescription")
    suspend fun getInvoice() : List<PaymentDescription>

    @Query("DELETE FROM PaymentDescription")
    suspend fun deleteInvoice()
}