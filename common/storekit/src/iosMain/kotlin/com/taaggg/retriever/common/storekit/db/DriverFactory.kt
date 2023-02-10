package com.taaggg.retriever.common.storekit.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.taaggg.retriever.common.storekit.RetrieverDatabase

actual class DriverFactory {
    actual suspend fun createDriver(): SqlDriver = NativeSqliteDriver(RetrieverDatabase.Schema, "retriever.database")
}