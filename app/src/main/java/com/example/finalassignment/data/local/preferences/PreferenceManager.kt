package com.example.finalassignment.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore by  preferencesDataStore("settings")
val DARK_MODE = booleanPreferencesKey("dark_mode")

object PreferenceManager {
    suspend fun saveDarkMode(context: Context, enabled: Boolean) {
        context.dataStore.edit {settings ->
            settings[DARK_MODE] = enabled
        }
    }
    fun getDarkMode(context: Context): Flow<Boolean> =
        context.dataStore.data.map {settings ->
            settings[DARK_MODE] ?: false
        }
}