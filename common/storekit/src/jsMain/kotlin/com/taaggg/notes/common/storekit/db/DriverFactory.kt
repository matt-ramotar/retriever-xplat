package com.taaggg.notes.common.storekit.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.sqljs.initSqlDriver
import com.taaggg.notes.common.storekit.NotesDatabase
import kotlinx.coroutines.await

actual class DriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        return initSqlDriver(NotesDatabase.Schema).await()
    }
}