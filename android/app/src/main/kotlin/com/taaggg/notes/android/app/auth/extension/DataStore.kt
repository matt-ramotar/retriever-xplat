package com.taaggg.notes.android.app.auth.extension

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.taaggg.notes.android.app.auth.TOKEN_KEY
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

suspend fun DataStore<Preferences>.tokenOrNull(): String? =
    data.map { preferences -> preferences[stringPreferencesKey(TOKEN_KEY)] }.firstOrNull()
