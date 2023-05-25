package com.upaep.colegios.data.base.preferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.ui.graphics.Color
import com.google.gson.Gson
import com.upaep.colegios.data.entities.studentselector.StudentsSelector

class UserPreferences private constructor() {

    companion object {
        private const val IS_LOGGED = "RJ45"
        private val CURRENT_LOCKSMITH = "CURRENT_LOCKSMITH"
        private const val CHILD_NAME = "CURRENT_CHILD"
        private val LEVEL_COLOR = "CURRENT_COLOR"
        private val CHILD_DATA = "CHILD_DATA"

        private val sharePref = UserPreferences()
        private lateinit var sharedPreferences: SharedPreferences
        fun getInstance(context: Context): UserPreferences {
            if (!::sharedPreferences.isInitialized) {
                synchronized(UserPreferences::class.java) {
                    if (!::sharedPreferences.isInitialized) {
                        sharedPreferences =
                            context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
                    }
                }
            }
            return sharePref
        }
    }

    fun getChildSelectedData() : StudentsSelector {
        val studentSelected = sharedPreferences.getString(CHILD_DATA, "")
        return Gson().fromJson(studentSelected, StudentsSelector::class.java)
    }

    fun setChildSelectedData(studentData: StudentsSelector) {
        val editor = sharedPreferences.edit()
        editor.putString(CHILD_DATA, Gson().toJson(studentData))
        editor.apply()
    }

    fun getChildSelected(): String? {
        return sharedPreferences.getString(CHILD_NAME, "")
    }

    fun setChildSelected(name: String) {
        val editor = sharedPreferences.edit()
        editor.putString(CHILD_NAME, name)
        editor.apply()
    }

    fun isLogged(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGGED, false)
    }

    fun setLogged(value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(IS_LOGGED, value)
        editor.apply()
    }

    fun setColor(color: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(LEVEL_COLOR, color)
        editor.apply()
    }

    fun getColor(): Int {
        return sharedPreferences.getInt(LEVEL_COLOR, 0)
    }
}