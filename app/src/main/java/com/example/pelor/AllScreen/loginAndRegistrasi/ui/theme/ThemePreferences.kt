package com.example.pelor.AllScreen.loginAndRegistrasi.ui.theme

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

private val Context.dataStore by preferencesDataStore("theme_prefs")

class ThemeViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = ThemePreferences(application)

    val isDarkMode: Flow<Boolean> = prefs.isDarkMode

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            prefs.setDarkMode(enabled)
        }
    }
}
class ThemePreferences(private val context: Context) {
    companion object {
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }

    val isDarkMode: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[DARK_MODE_KEY] ?: false }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_MODE_KEY] = enabled
        }
    }
}

class ThemeViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ThemeViewModel(app) as T
    }
}