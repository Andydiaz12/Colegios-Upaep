package com.upaep.colegios.data.base.room

import android.content.Context
import androidx.room.Room
import com.upaep.colegios.data.database.homedao.HomeDao
import com.upaep.colegios.data.database.locksmithdao.LocksmithDao
import com.upaep.colegios.data.database.logindao.LoginDao
import com.upaep.colegios.data.database.sessiondao.SessionDao
import com.upaep.colegios.data.database.ColegiosDatabase
import com.upaep.colegios.data.database.scheduledao.ScheduleDao
import com.upaep.colegios.data.database.studentselectordao.StudentSelectorDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providesLoginDao(colegiosDatabase: ColegiosDatabase): LoginDao {
        return colegiosDatabase.loginDao()
    }

    @Provides
    fun providesHomeDao(colegiosDatabase: ColegiosDatabase) : HomeDao {
        return colegiosDatabase.homeDao()
    }

    @Provides
    fun providesLocksmithDao(colegiosDatabase: ColegiosDatabase) : LocksmithDao {
        return colegiosDatabase.locksmithDao()
    }

    @Provides
    fun providesSession(colegiosDatabase: ColegiosDatabase) : SessionDao {
        return colegiosDatabase.sessionDao()
    }

    @Provides
    fun providesStudentSelector(colegiosDatabase: ColegiosDatabase) : StudentSelectorDao {
        return colegiosDatabase.studentSelectorDao()
    }

    @Provides
    fun providesSchedule(colegiosDatabase: ColegiosDatabase) : ScheduleDao {
        return colegiosDatabase.scheduleDao()
    }

    @Provides
    @Singleton
    fun provideColegiosDatabase(@ApplicationContext appContext: Context): ColegiosDatabase {
        return Room.databaseBuilder(appContext, ColegiosDatabase::class.java, "ColegiosDatabase")
            .fallbackToDestructiveMigration()
            .build()
    }
}