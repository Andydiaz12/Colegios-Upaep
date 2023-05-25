package com.upaep.colegios.data.database.studentselectordao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upaep.colegios.data.entities.studentselector.StudentsSelector
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentSelectorDao {

    @Query("DELETE FROM StudentsSelector")
    suspend fun deleteStudents()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertStudents(item: List<StudentsSelector>)

    @Query("SELECT * FROM StudentsSelector")
    fun getStudents(): Flow<List<StudentsSelector>>
}