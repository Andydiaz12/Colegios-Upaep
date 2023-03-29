package com.upaep.colegios.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.upaep.colegios.data.base.room.logindao.LoginDao
import com.upaep.colegios.data.entities.login.UserData

@Database(entities = [UserData::class], version = 1)
abstract class ColegiosDatabase : RoomDatabase() {

    abstract fun loginDao(): LoginDao
}