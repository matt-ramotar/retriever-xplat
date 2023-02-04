package com.taaggg.notes.common.storekit.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import com.taaggg.notes.common.storekit.NotesDatabase

actual class DriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        NotesDatabase.Schema.create(driver)
        return driver
    }
}