package com.upaep.colegios.data.database.homedao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.upaep.colegios.data.entities.home.Feature
import com.upaep.colegios.data.entities.login.UserData

@Dao
interface HomeDao {
    @Insert
    suspend fun addFeatures(item: List<Feature>)

    @Query("SELECT * FROM Feature")
    suspend fun getFeatures(): List<Feature>

    @Query("DELETE FROM Feature")
    suspend fun deleteFeatures()
}