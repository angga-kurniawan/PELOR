package com.example.pelor.Service

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.userDataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {
    companion object {
        val UID_KEY = stringPreferencesKey("user_uid")
        val LANGUAGE_LABEL_KEY = stringPreferencesKey("language_label")
    }

    val uid: Flow<String?> = context.userDataStore.data.map { prefs ->
        prefs[UID_KEY]
    }

    val languageLabel: Flow<String> = context.userDataStore.data
        .map { prefs -> prefs[LANGUAGE_LABEL_KEY] ?: "Indonesia" }

    suspend fun saveUid(uid: String) {
        context.userDataStore.edit { prefs ->
            prefs[UID_KEY] = uid
        }
    }

    suspend fun clearUid() {
        context.userDataStore.edit { prefs ->
            prefs.remove(UID_KEY)
        }
    }

    suspend fun setLanguageLabel(label: String) {
        context.userDataStore.edit { prefs ->
            prefs[LANGUAGE_LABEL_KEY] = label
        }
    }
}