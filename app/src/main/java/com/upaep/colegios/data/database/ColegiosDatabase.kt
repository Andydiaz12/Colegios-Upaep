package com.upaep.colegios.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.upaep.colegios.data.database.homedao.HomeDao
import com.upaep.colegios.data.database.locksmithdao.LocksmithDao
import com.upaep.colegios.data.database.logindao.LoginDao
import com.upaep.colegios.data.database.scheduledao.ScheduleDao
import com.upaep.colegios.data.database.sessiondao.SessionDao
import com.upaep.colegios.data.database.studentselectordao.StudentSelectorDao
import com.upaep.colegios.data.entities.home.Feature
import com.upaep.colegios.data.entities.locksmith.Locksmith
import com.upaep.colegios.data.entities.login.UserData
import com.upaep.colegios.data.entities.schedule.DayClass
import com.upaep.colegios.data.entities.studentselector.StudentsSelector

@Database(
    entities = [
        Locksmith::class,
        UserData::class,
        Feature::class,
        StudentsSelector::class,
        DayClass::class
    ], version = 8
)
abstract class ColegiosDatabase : RoomDatabase() {
    abstract fun loginDao(): LoginDao
    abstract fun homeDao(): HomeDao
    abstract fun locksmithDao(): LocksmithDao
    abstract fun sessionDao(): SessionDao
    abstract fun studentSelectorDao(): StudentSelectorDao
    abstract fun scheduleDao(): ScheduleDao
}