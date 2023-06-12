package com.upaep.colegios.model.base

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.upaep.colegios.model.entities.studentselector.StudentsSelector
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json


class UserPreferences(context: Context) {

    private var pref = context.dataStore

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore("USER_PREFERENCES")
        var IS_LOGGED = booleanPreferencesKey("IS_LOGGED")
        var SELECTED_STUDENT = stringPreferencesKey("SELECTED_STUDENT")
        var BASE_COLOR = intPreferencesKey("BASE_COLOR")
    }

    val getSelectedStudent: Flow<StudentsSelector> = pref.data.map {
        Json.decodeFromString(
            deserializer = StudentsSelector.serializer(),
            string = it[SELECTED_STUDENT] ?: Json.encodeToString(
                serializer = StudentsSelector.serializer(),
                value = StudentsSelector(name = "", perseq = "", persclv = "", school = "")
            )
        )
    }

    val getBaseColor: Flow<Color> = pref.data.map {
        Color(it[BASE_COLOR] ?: 0)
    }

    suspend fun setSelectedStudent(studentSelected: StudentsSelector, color: Color) {
        pref.edit {
            it[BASE_COLOR] = color.toArgb()
            it[SELECTED_STUDENT] = Json.encodeToString(
                serializer = StudentsSelector.serializer(),
                value = studentSelected
            )
        }
    }

    suspend fun setLogged(logged: Boolean) {
        pref.edit {
            it[IS_LOGGED] = logged
        }
    }

    suspend fun isLogged(): Boolean {
        val isLoggedFlow = pref.data.map { it[IS_LOGGED] }
        return isLoggedFlow.first() ?: false
    }
}