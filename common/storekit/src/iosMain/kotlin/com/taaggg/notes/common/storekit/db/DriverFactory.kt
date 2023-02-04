package com.taaggg.notes.common.storekit.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.taaggg.notes.common.storekit.NotesDatabase

actual class DriverFactory {
    actual suspend fun createDriver(): SqlDriver = NativeSqliteDriver(NotesDatabase.Schema, "notes.database")
}