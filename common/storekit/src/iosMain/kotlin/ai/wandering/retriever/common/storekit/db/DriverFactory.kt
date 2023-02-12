package ai.wandering.retriever.common.storekit.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import ai.wandering.retriever.common.storekit.RetrieverDatabase

actual class DriverFactory {
    actual suspend fun createDriver(): SqlDriver = NativeSqliteDriver(RetrieverDatabase.Schema, "retriever.database")
}