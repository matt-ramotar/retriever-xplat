package com.taaggg.retriever.common.storekit.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import com.taaggg.retriever.common.storekit.RetrieverDatabase

actual class DriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        RetrieverDatabase.Schema.create(driver)
        return driver
    }
}