package com.upaep.colegios.model.database.accountbalancedao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.upaep.colegios.model.entities.accountbalance.AccountBalance

@Dao
interface AccountBalanceDao {
    @Insert
    suspend fun insertAccountBalance(item: List<AccountBalance>)

    @Query("SELECT * FROM AccountBalance")
    fun getAccountBalance() : List<AccountBalance>

    @Query("DELETE FROM AccountBalance")
    suspend fun deleteAccountBalance()
}