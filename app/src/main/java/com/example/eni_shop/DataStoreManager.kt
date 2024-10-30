package com.example.eni_shop

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    private val Context.dataStore by preferencesDataStore("user_preferences")
    private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")

    // récupérer la préférence du thème
    val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_THEME_KEY] ?: false
        }

    // éditer la préférence du thème
    suspend fun setDarkTheme(isDarkTheme: Boolean) {
        context.dataStore.edit { preferences -> preferences[DARK_THEME_KEY] = isDarkTheme }
    }
}