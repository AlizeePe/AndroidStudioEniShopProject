package com.example.eni_shop.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object DataStoreManager {

    private val Context.dataStore by preferencesDataStore("settings")
    private val DARK_THEME_KEY = booleanPreferencesKey("DARK_MODE_KEY")

    // récupérer la préférence du thème
    fun isDarkModeActivated(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[DARK_THEME_KEY] ?: false
        }
    }

    // éditer la préférence du thème
    suspend fun setDarkTheme(context: Context, isDarkTheme: Boolean) {
        context.dataStore.edit { preferences -> preferences[DARK_THEME_KEY] = isDarkTheme }
    }
}