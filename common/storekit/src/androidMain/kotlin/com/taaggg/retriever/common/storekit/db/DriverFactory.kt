package com.taaggg.retriever.common.storekit.db

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.taaggg.retriever.common.storekit.RetrieverDatabase

actual class DriverFactory(private val context: Context) {
    actual suspend fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(RetrieverDatabase.Schema, context, "retriever.database")
    }
}