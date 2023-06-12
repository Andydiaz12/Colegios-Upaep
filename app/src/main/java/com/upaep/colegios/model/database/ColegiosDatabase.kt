package com.upaep.colegios.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.upaep.colegios.model.database.homedao.HomeDao
import com.upaep.colegios.model.database.locksmithdao.LocksmithDao
import com.upaep.colegios.model.database.logindao.LoginDao
import com.upaep.colegios.model.database.scheduledao.ScheduleDao
import com.upaep.colegios.model.database.sessiondao.SessionDao
import com.upaep.colegios.model.database.studentselectordao.StudentSelectorDao
import com.upaep.colegios.model.entities.home.Feature
import com.upaep.colegios.model.entities.locksmith.Locksmith
import com.upaep.colegios.model.entities.login.UserData
import com.upaep.colegios.model.entities.schedule.DayClass
import com.upaep.colegios.model.entities.studentselector.StudentsSelector

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