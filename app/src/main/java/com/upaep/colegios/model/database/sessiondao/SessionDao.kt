package com.upaep.colegios.model.database.sessiondao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.upaep.colegios.model.entities.login.UserData

@Dao
interface SessionDao {
    @Query("SELECT * FROM UserData")
    suspend fun getSession(): List<UserData>

    @Insert
    suspend fun createSession(item: UserData)

    @Query("DELETE FROM UserData")
    suspend fun deleteSession()
}