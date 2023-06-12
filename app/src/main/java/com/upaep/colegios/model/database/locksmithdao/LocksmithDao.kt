package com.upaep.colegios.model.database.locksmithdao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.upaep.colegios.model.entities.locksmith.Locksmith

@Dao
interface LocksmithDao {
    @Insert
    suspend fun addLocksmith(locksmith: Locksmith)

    @Query("SELECT * FROM Locksmith")
    suspend fun getLocksmith() : Locksmith

    @Query("DELETE FROM Locksmith")
    suspend fun deleteLocksmith()
}