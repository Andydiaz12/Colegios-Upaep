package com.upaep.colegios.data.base.room

import android.content.Context
import androidx.room.Room
import com.upaep.colegios.data.base.room.logindao.LoginDao
import com.upaep.colegios.data.database.ColegiosDatabase
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
    @Singleton
    fun provideColegiosDatabase(@ApplicationContext appContext: Context): ColegiosDatabase {
        return Room.databaseBuilder(appContext, ColegiosDatabase::class.java, "ColegiosDatabase")
            .build()
    }
}