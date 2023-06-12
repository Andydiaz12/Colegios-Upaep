package com.upaep.colegios.model.database.studentselectordao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upaep.colegios.model.entities.studentselector.StudentsSelector

@Dao
interface StudentSelectorDao {

    @Query("DELETE FROM StudentsSelector")
    suspend fun deleteStudents()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertStudents(item: List<StudentsSelector>)

    @Query("SELECT * FROM StudentsSelector")
    fun getStudents(): List<StudentsSelector>
}