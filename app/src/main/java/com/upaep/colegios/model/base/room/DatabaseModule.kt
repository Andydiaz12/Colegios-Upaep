package com.upaep.colegios.model.base.room

import android.content.Context
import androidx.room.Room
import com.upaep.colegios.model.database.homedao.HomeDao
import com.upaep.colegios.model.database.locksmithdao.LocksmithDao
import com.upaep.colegios.model.database.logindao.LoginDao
import com.upaep.colegios.model.database.sessiondao.SessionDao
import com.upaep.colegios.model.database.ColegiosDatabase
import com.upaep.colegios.model.database.scheduledao.ScheduleDao
import com.upaep.colegios.model.database.studentselectordao.StudentSelectorDao
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