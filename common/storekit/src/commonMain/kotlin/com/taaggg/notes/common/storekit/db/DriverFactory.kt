package com.taaggg.notes.common.storekit.db

import com.squareup.sqldelight.db.SqlDriver

expect class DriverFactory {
    suspend fun createDriver(): SqlDriver
}