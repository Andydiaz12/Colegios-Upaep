package com.upaep.colegios.model.database.scheduledao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upaep.colegios.model.entities.schedule.DayClass

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM DayClass")
    fun getClasses(): List<DayClass>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertClasses(item: List<DayClass>)

    @Query("DELETE FROM DayClass")
    suspend fun deleteClasses()
}