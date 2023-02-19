package ai.wandering.retriever.android.app.datastore

import ai.wandering.retriever.common.storekit.entity.AuthenticatedUser
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val USER_DATA_STORE = "USER_DATA_STORE"
val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = USER_DATA_STORE)
suspend fun DataStore<Preferences>.authenticatedUser(serializer: Json, userId: String): AuthenticatedUser = data
    .map { preferences -> requireNotNull(preferences[stringPreferencesKey(userId)]) }
    .first()
    .let { serializer.decodeFromString(it) }
